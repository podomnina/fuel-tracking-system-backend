package com.startup.controller;

import com.startup.model.entities.Ship;
import com.startup.model.requestbody.ShipBody;
import com.startup.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.startup.controller.ApiPath.ID_PATH;
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
            ShipBody shipBody) {
        return new ResponseEntity<>(shipService.createShip(shipBody), HttpStatus.OK);
    }

    @RequestMapping(value = SHIP_PATH, method = GET)
    public ResponseEntity<List<Ship>> getAllShips() {
        return new ResponseEntity<>(shipService.getAllShips(), HttpStatus.OK);
    }

    @RequestMapping(value = SHIP_PATH + ID_PATH, method = GET)
    public ResponseEntity<Ship> getShip(@PathVariable Long id) {
        return new ResponseEntity<>(shipService.getShip(id), HttpStatus.OK);
    }

    @RequestMapping(value = SHIP_PATH, method = DELETE)
    public ResponseEntity<Object> deleteShip(@PathVariable Long id) {
        shipService.deleteShip(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
