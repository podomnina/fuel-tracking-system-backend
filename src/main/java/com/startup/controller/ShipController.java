package com.startup.controller;

import com.startup.model.entities.Ship;
import com.startup.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.startup.controller.ApiPath.SHIP_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ShipController {
    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(value = SHIP_PATH, method = POST)
    public ResponseEntity<Long> createShip(
            @RequestBody
                    String name) {
        return new ResponseEntity<>(shipService.createShip(name), HttpStatus.OK);
    }

    @RequestMapping(value = SHIP_PATH, method = GET)
    public ResponseEntity<List<Ship>> getAllShips() {
        return new ResponseEntity<>(shipService.getAllShips(), HttpStatus.OK);
    }
}
