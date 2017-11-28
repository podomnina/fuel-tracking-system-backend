package com.startup.service;

import com.startup.model.entities.Coordinates;
import com.startup.model.entities.Port;
import com.startup.model.entities.Route;
import com.startup.model.entities.Ship;
import com.startup.model.requestbody.RouteBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class TrackingService {

    private final RouteService routeService;
    private final ShipService shipService;
    private final PortService portService;

    @Autowired
    public TrackingService(RouteService routeService,
                           ShipService shipService,
                           PortService portService) {
        this.routeService = routeService;
        this.shipService = shipService;
        this.portService = portService;
    }

    public void runShip(RouteBody routeBody) {
        //final Route route = routeService.createRoute(routeBody);
        final Route route = new Route();
        route.setShip(new Ship());
        Port port1 = new Port(1L, 0,50);
        Port port2 = new Port(2L, 0,0);
        route.setRoutePoints(Arrays.asList(port1, port2));
        if (!isEmpty(route.getRoutePoints())) {
            Port firstPort = route.getRoutePoints().get(0);
            Port secondPort = route.getRoutePoints().get(1);
            Coordinates point = new Coordinates(firstPort.getLatitude(), firstPort.getLongitude());
            Coordinates unitVector = getUnitVector(firstPort, secondPort);
            for (int i = 0; i < 10; i++) {

                point = new Coordinates(point.getLatitude() + unitVector.getLatitude()*i,
                        point.getLongitude() + unitVector.getLongitude()*i);
            }

        }
    }

    //latitude - x, longitude - y
    private void moveShip(Long id, float latitude, float longitude) {
        Ship ship = shipService.getShip(id);
        ship.setCurrentCoodrinates(latitude, longitude);
        shipService.modifyShip(ship);
    }

    private Coordinates getUnitVector(Port port1, Port port2) {
        final Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(port2.getLatitude() - port1.getLatitude());
        coordinates.setLongitude(port2.getLongitude() - port1.getLongitude());
        final float length = (float) Math.sqrt(coordinates.getLatitude()*coordinates.getLatitude() +
                coordinates.getLongitude()*coordinates.getLongitude());
        coordinates.setLatitude(coordinates.getLatitude()/length);
        coordinates.setLongitude(coordinates.getLongitude()/length);
        return coordinates;
    }

    private float getK(Port port1, Port port2) {
        float deltaY = port1.getLongitude() - port2.getLongitude();
        float deltaX = port1.getLatitude() - port2.getLatitude();
        return deltaY/deltaX;
    }

    private float getB(Port port1, Port port2) {
        float k = getK(port1, port2);
        return port1.getLongitude()-k*port1.getLatitude();
    }

}
