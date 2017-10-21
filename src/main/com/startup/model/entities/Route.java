package com.startup.model.entities;

import lombok.Data;

import java.util.List;

@Data
public class Route extends AbstractEntity {
    private Port pointOfDeparture;
    private Port arrivalPoint;
    private List<Port> intermediatePoint;
    private Long distance;
    private float amountOfFuel;
}
