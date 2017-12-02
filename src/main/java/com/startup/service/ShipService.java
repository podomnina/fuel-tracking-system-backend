package com.startup.service;

import com.startup.model.entities.Ship;
import com.startup.model.enums.CustomShips;
import com.startup.model.requestbody.ShipBody;
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

    public Long createShip(ShipBody shipBody) {
        final Ship ship = new Ship(shipBody);
        shipRepository.save(ship);
        return ship.getId();
    }

    public void deleteShip(Long id) {
        shipRepository.delete(id);
    }

    public Ship getShip(Long id) {
        return shipRepository.findOne(id);
    }

    public List<Ship> getAllShips() {
        return newArrayList(shipRepository.findAll());
    }

    public void modifyShip(Ship ship) {
        shipRepository.save(ship);
    }

    public boolean isExist(Long id) {
        return shipRepository.exists(id);
    }

    public void createShipsIfNotExist() {
        CustomShips.getAll().forEach(customShips -> {
            if (isExist(customShips.getShip().getId())) {
                shipRepository.save(customShips.getShip());
            }
        });

    }
}
