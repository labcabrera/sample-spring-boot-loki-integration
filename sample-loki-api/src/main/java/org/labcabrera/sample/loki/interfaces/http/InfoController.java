package org.labcabrera.sample.loki.interfaces.http;

import org.labcabrera.sample.loki.configuration.ApiInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Info", description = "API Information")
@RestController
@RequestMapping("/api/info")
public class InfoController {

    private final ApiInfo apiInfo;

    public InfoController(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    @GetMapping
    public ApiInfo get() {
        return apiInfo;
    }
}
