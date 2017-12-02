package com.startup.model.enums;

import com.startup.model.entities.Ship;

import java.util.Arrays;
import java.util.List;

public enum CustomShips {
    SHIP_1(1L, "Ласточка"),
    SHIP_2(2L, "Гавань"),
    SHIP_3(3L, "Василий Иванович");

    CustomShips(Long id, String name) {
        this.ship = new Ship();
        this.ship.setId(id);
        this.ship.setName(name);
    }

    public Ship getShip() {
        return this.ship;
    }

    public static List<CustomShips> getAll() {
        return Arrays.asList(values());
    }

    private Ship ship;
}
