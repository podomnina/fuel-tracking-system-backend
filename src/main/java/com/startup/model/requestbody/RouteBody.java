package com.startup.model.requestbody;

import com.startup.model.entities.Port;
import lombok.Data;

import java.util.List;

@Data
public class RouteBody {
    private Long id;
    private Long shipId;
    private Long fromId;
    private Long toId;
    private float amountOfFuel;
}
