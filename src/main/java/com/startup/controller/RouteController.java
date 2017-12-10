package com.startup.controller;

import com.startup.model.entities.Route;
import com.startup.model.requestbody.RouteBody;
import com.startup.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.startup.controller.ApiPath.ID_PATH;
import static com.startup.controller.ApiPath.ROUTE_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping(value = ROUTE_PATH, method = POST)
    public ResponseEntity<Long> createRoute(
            @RequestBody
                    RouteBody routeBody) {
        return new ResponseEntity<>(routeService.addRoute(routeBody), HttpStatus.OK);
    }

    @RequestMapping(value = ROUTE_PATH, method = GET)
    public ResponseEntity<List<Route>> getAllRoute() {
        return new ResponseEntity<>(routeService.getAllRoutes(), HttpStatus.OK);
    }

    @RequestMapping(value = ROUTE_PATH + ID_PATH, method = GET)
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        return new ResponseEntity<>(routeService.getRoute(id), HttpStatus.OK);
    }

    @RequestMapping(value = ROUTE_PATH, method = DELETE)
    public ResponseEntity deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}