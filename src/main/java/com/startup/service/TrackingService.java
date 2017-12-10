package com.startup.service;

import com.startup.model.entities.Coordinates;
import com.startup.model.entities.Port;
import com.startup.model.entities.Route;
import com.startup.model.entities.Ship;
import com.startup.model.requestbody.RouteBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Transactional
public class TrackingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public void runShip(Ship ship, Route route, int step) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        final Port from = ship.getCurrentFrom();
        final Port to = ship.getCurrentTo();
        final Coordinates unitVector = getUnitVector(from, to);
        final Coordinates currentPoint = new Coordinates(ship.getCurrentLatitude(), ship.getCurrentLongitude());
        final Coordinates nextPoint = new Coordinates((currentPoint.getLatitude() + unitVector.getLatitude()*step),
                (currentPoint.getLongitude() + unitVector.getLongitude()*step));
        ship.setCurrentCoodrinates(nextPoint.getLatitude(), nextPoint.getLongitude());
        if (((nextPoint.getLatitude() <= to.getLatitude() + 0.5) && (nextPoint.getLatitude() >= to.getLatitude() - 0.5) )
                && ((nextPoint.getLongitude() <= to.getLongitude() + 0.5) && (nextPoint.getLongitude() >= to.getLongitude() - 0.5) )) {
            //setting info
            final String shipIsArrived = sdf.format(cal.getTime()) + " Судно " + ship.getName() + " прибыло в порт " + to.getName();

            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(route.getInfo());
            stringBuilder.append("\n").append(shipIsArrived);

            System.out.println(shipIsArrived);

            //rewrite current from/to ports
            ship.setCurrentFrom(to);
            final Map<Long, Port> portsToId = route.getRoutePoints().stream().collect(Collectors.toMap(Port::getId, Function.identity()));
            final Port newTo = portsToId.get( route.isReverse() ? to.getId() -1 : to.getId() + 1);
            if (newTo != null) {
                ship.setCurrentTo(newTo);
                ship.setCurrentCoodrinates(ship.getCurrentFrom().getLatitude(), ship.getCurrentFrom().getLongitude());
            } else {
                float fuel = route.getAmountOfFuel() - ship.getCurrentFuel();
                stringBuilder.append("\n").append(sdf.format(cal.getTime())).append(" Судно прибыло в пункт назначения. Потрачено топлива: " + fuel + "л");
                route.setArchived(true);
                ship.setCurrentCoodrinates(ship.getCurrentTo().getLatitude(), ship.getCurrentTo().getLongitude());
            }

            route.setInfo(stringBuilder.toString());
        }
        ship.setCurrentFuel(ship.getCurrentFuel() - generateFuelValue());

        routeService.modifyRoute(route);
        shipService.modifyShip(ship);
    }

    private float generateFuelValue() {
        double fatal = Math.random();
        Random rnd = new Random(System.currentTimeMillis());
        int fuel = 5 + rnd.nextInt(10 - 5 + 1);
        if (fatal < 0.005) {
            return 40;
        } else {
            return fuel;
        }
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

    public void createDataIfNotExist() {
        portService.createPortsIfNotExists();
        shipService.createShipsIfNotExist();
    }

    @Scheduled(fixedDelay = 1000)
    public void moveShips() {
        System.out.println("Moving ships");
        final List<Route> routes = routeService.getAllRoutes();
        if (!isEmpty(routes)) {
            routes.stream().filter(route -> !route.isArchived()).forEach(route -> {
                if (route.getShip() != null) {
                    runShip(route.getShip(), route, 1);
                }
            });
        }
    }

}
