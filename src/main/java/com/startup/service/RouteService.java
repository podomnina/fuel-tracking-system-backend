package com.startup.service;

import com.startup.model.entities.Port;
import com.startup.model.entities.Route;
import com.startup.model.entities.Ship;
import com.startup.model.requestbody.RouteBody;
import com.startup.repositories.RouteRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
@Transactional
public class RouteService {
    private final RouteRepository routeRepository;
    private final ShipService shipService;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        ShipService shipService) {
        this.routeRepository = routeRepository;
        this.shipService = shipService;
    }

    public Long addRoute(RouteBody routeBody) {
        return createRoute(routeBody).getId();
    }

    public Route createRoute(RouteBody routeBody) {
        final Route route = new Route(routeBody);
        final Route savedRoute = routeRepository.save(route);
        final Ship ship = shipService.getShip(savedRoute.getShip().getId());
        if (savedRoute.getRoutePoints() != null && savedRoute.getRoutePoints().size() >= 2) {
            final Port fromPort = savedRoute.getRoutePoints().get(0);
            final Port toPort = savedRoute.getRoutePoints().get(1);
            ship.setCurrentCoodrinates(fromPort.getLatitude(), fromPort.getLongitude());
            ship.setCurrentFrom(fromPort);
            ship.setCurrentTo(toPort);
            ship.setCurrentFuel(savedRoute.getAmountOfFuel());
            shipService.modifyShip(ship);
        }
        return savedRoute;
    }

    public void modifyRoute(Route route) {
        routeRepository.save(route);
    }

    public void deleteRoute(Long id) {
        routeRepository.delete(id);
    }

    public Route getRoute(Long id) {
        final Route route = routeRepository.findOne(id);
        Hibernate.initialize(route);
        return route;
    }

    public List<Route> getAllRoutes() {
        final List<Route> routes = newArrayList(routeRepository.findAll());
        Hibernate.initialize(routes);
        return routes;
    }
}
