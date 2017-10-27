package com.startup.service;

import com.startup.model.entities.Route;
import com.startup.model.requestbody.RouteBody;
import com.startup.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Long createRoute(RouteBody routeBody) {
        final Route route = new Route(routeBody);
        routeRepository.save(route);
        return route.getId();
    }

    public void deleteRoute(Long id) {
        routeRepository.delete(id);
    }

    public Route getRoute(Long id) {
        return routeRepository.findOne(id);
    }

    public List<Route> getAllRoutes() {
        return newArrayList(routeRepository.findAll());
    }
}
