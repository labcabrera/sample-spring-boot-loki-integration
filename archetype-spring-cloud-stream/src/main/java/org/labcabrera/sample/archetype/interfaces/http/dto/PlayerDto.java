package org.labcabrera.sample.archetype.interfaces.http.dto;

import lombok.Data;

@Data
public class PlayerDto {

    private String id;
    private String name;
    private String email;
    private Integer elo;

}
