package com.startup.model.entities;

import lombok.Data;

@Data
public class Port extends AbstractEntity {
    private String name;
    private Coordinates coordinates;
}
