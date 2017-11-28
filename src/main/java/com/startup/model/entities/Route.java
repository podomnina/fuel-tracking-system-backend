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
    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;
    @OneToMany
    @JoinColumn(name = "intermediate_point_id")
    private List<Port> routePoints;
    private Long distance;
    private float amountOfFuel;

    public Route(RouteBody routeBody) {
        this.setShip(new Ship(routeBody.getShipId()));
        this.setRoutePoints(routeBody.getRoutePointIds() != null
                ? routeBody.getRoutePointIds().stream().filter(Objects::nonNull).map(Port::new).collect(toList())
                : new ArrayList<>());
        this.distance = routeBody.getDistance();
        this.amountOfFuel = routeBody.getAmountOfFuel();
    }
}
