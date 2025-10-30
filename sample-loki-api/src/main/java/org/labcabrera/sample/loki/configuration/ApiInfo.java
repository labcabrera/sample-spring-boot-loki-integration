package org.labcabrera.sample.loki.configuration;

import lombok.Value;

@Value
public class ApiInfo {
    private final String title;
    private final String description;
    private final String version;
}
