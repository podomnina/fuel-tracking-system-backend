package com.startup.model.requestbody;

import lombok.Data;

@Data
public class PortBody {
    private Long id;
    private String name;
    private float latitude;
    private float longitude;
}
