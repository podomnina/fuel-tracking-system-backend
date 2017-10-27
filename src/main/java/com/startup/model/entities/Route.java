package com.startup.model.entities;

import com.startup.model.requestbody.RouteBody;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Port pointOfDeparture;
    private Port arrivalPoint;
    private List<Port> intermediatePoint;
    private Long distance;
    private float amountOfFuel;

    public Route(RouteBody routeBody) {
        this.setPointOfDeparture(new Port(routeBody.getId()));
        this.setArrivalPoint(new Port(routeBody.getId()));
        this.setIntermediatePoint(routeBody.getIntermediatePointIds().stream().map(Port::new).collect(toList()));
        this.distance = routeBody.getDistance();
        this.amountOfFuel = routeBody.getAmountOfFuel();
    }
}
