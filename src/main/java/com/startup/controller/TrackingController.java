package com.startup.controller;

import com.startup.model.requestbody.RouteBody;
import com.startup.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class TrackingController {
    private final TrackingService trackingService;

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @RequestMapping(value = "/tracking", method = POST)
    public ResponseEntity<Long> start(@RequestBody
                                      RouteBody routeBody) {
        trackingService.runShip(routeBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/tracking/create/data", method = POST)
    public ResponseEntity createCustomData() {
        trackingService.createDataIfNotExist();
        return new ResponseEntity(HttpStatus.OK);
    }

}
