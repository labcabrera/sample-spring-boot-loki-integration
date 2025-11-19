package org.labcabrera.sample.archetype.shared.infrastructure.security;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.labcabrera.sample.archetype.shared.application.SecurityPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAdapter implements SecurityPort {

    @Override
    public Optional<AuthenticatedUser> currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String id = jwt.getSubject();
            String username = jwt.getClaimAsString("preferred_username"); // or "sub"
            Set<String> roles = extractRoles(jwt); // map realm_access / resource_access etc.
            Set<String> scopes = extractScopes(jwt);
            return Optional.of(new AuthenticatedUser(id, username, roles, scopes));
        }
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            String username = ud.getUsername();
            Set<String> roles = new HashSet<>();
            ud.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
            return Optional.of(new AuthenticatedUser(null, username, roles, Set.of()));
        }
        return Optional.empty();
    }

    private Set<String> extractRoles(Jwt jwt) {
        Set<String> roles = new HashSet<>();
        // realm_access.roles
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null) {
            Object r = realmAccess.get("roles");
            if (r instanceof List) {
                ((List<?>) r).forEach(x -> roles.add(String.valueOf(x)));
            }
        }
        // resource_access.{client}.roles
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null) {
            resourceAccess.values().forEach(v -> {
                if (v instanceof Map) {
                    Object rr = ((Map<?, ?>) v).get("roles");
                    if (rr instanceof List) {
                        ((List<?>) rr).forEach(x -> roles.add(String.valueOf(x)));
                    }
                }
            });
        }
        // authorities in scopes or custom claims may already include ROLE_ prefix
        return roles;
    }

    private Set<String> extractScopes(Jwt jwt) {
        Set<String> scopes = new HashSet<>();
        List<String> scopeList = jwt.getClaimAsStringList("scope");
        if (scopeList != null && !scopeList.isEmpty()) {
            scopeList.forEach(s -> scopes.add(s));
            return scopes;
        }
        String scopeStr = jwt.getClaimAsString("scope");
        if (scopeStr != null && !scopeStr.isBlank()) {
            for (String s : scopeStr.split(" ")) {
                scopes.add(s);
            }
        }
        // some tokens use 'scp'
        List<String> scp = jwt.getClaimAsStringList("scp");
        if (scp != null && !scp.isEmpty()) {
            scp.forEach(s -> scopes.add(s));
        }
        return scopes;
    }
}