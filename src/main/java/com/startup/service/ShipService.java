package com.startup.service;

import com.startup.model.entities.Ship;
import com.startup.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
public class ShipService {
    private final ShipRepository shipRepository;

    @Autowired
    public ShipService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public Long createShip(String name) {
        final Ship ship = new Ship();
        ship.setName(name);
        shipRepository.save(ship);
        return ship.getId();
    }

    public List<Ship> getAllShips() {
        return newArrayList(shipRepository.findAll());
    }
}
