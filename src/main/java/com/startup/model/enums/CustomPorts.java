package com.startup.model.enums;

import com.startup.model.entities.Port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public enum CustomPorts {
    SPB(1L, "Санкт-Петербург", 0, 26),
    RYBINSK(2L, "Рыбинск", 8, 20),
    NYZHNIY_NOVGOROD(3L, "Нижний Новгород", 11, 16),
    CHEBOKSARY(4L, "Чебоксары", 14, 15),
    KAZAN(5L, "Казань", 16, 14),
    ULIANOVSK(6L, "Ульяновск", 14, 12),
    SARATOV(7L, "Саратов", 11, 7),
    VOLGOGRAD(8L, "Волгоград", 9, 4),
    ASTRAHAN(9L, "Астрахань", 12, 0);

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
