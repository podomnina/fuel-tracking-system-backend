package com.startup.model.enums;

import com.startup.model.entities.Port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public enum CustomPorts {
    SPB(1L, "Санкт-Петербург", 0, 2600),
    RYBINSK(2L, "Рыбинск", 800, 2000),
    NYZHNIY_NOVGOROD(3L, "Нижний Новгород", 1100, 1600),
    CHEBOKSARY(4L, "Чебоксары", 1400, 1500),
    KAZAN(5L, "Казань", 1600, 1400),
    ULIANOVSK(6L, "Ульяновск", 1400, 1200),
    SARATOV(7L, "Саратов", 1100, 700),
    VOLGOGRAD(8L, "Волгоград", 900, 400),
    ASTRAHAN(9L, "Астрахань", 1200, 0);

    CustomPorts(Long id, String name, float latitude, float longitude) {
        this.port = new Port();
        this.port.setId(id);
        this.port.setName(name);
        this.port.setLatitude(latitude);
        this.port.setLongitude(longitude);
    }

    public Port getPort() {
        return this.port;
    }

    public static List<CustomPorts> getAll() {
        return Arrays.asList(values());
    }

    private final Port port;
}
