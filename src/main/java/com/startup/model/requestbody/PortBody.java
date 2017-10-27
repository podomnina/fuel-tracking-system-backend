package com.startup.model.requestbody;

import com.startup.model.entities.Coordinates;
import lombok.Data;

@Data
public class PortBody {
    private Long id;
    private String name;
    private Coordinates coordinates;
}
