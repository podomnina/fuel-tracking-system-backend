package com.startup.model.entities;

import com.startup.model.requestbody.RouteBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Data
@Entity
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "point_of_departure_id")
    private Port pointOfDeparture;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "arrival_point_id")
    private Port arrivalPoint;
    @OneToMany
    @JoinColumn(name = "intermediate_point_id")
    private List<Port> intermediatePoint;
    private Long distance;
    private float amountOfFuel;

    public Route(RouteBody routeBody) {
        this.setPointOfDeparture(new Port(routeBody.getPointOfDepartureId()));
        this.setArrivalPoint(new Port(routeBody.getArrivalPointId()));
        this.setIntermediatePoint(routeBody.getIntermediatePointIds() != null
                ? routeBody.getIntermediatePointIds().stream().filter(Objects::nonNull).map(Port::new).collect(toList())
                : new ArrayList<>());
        this.distance = routeBody.getDistance();
        this.amountOfFuel = routeBody.getAmountOfFuel();
    }
}
