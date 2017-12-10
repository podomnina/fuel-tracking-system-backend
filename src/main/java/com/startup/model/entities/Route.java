package com.startup.model.entities;

import com.google.common.collect.Lists;
import com.startup.model.enums.CustomPorts;
import com.startup.model.requestbody.RouteBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
@Entity
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;
    @OneToMany
    @JoinColumn(name = "intermediate_point_id")
    private List<Port> routePoints = new LinkedList<>();
    private float amountOfFuel;
    @Column(length = 1024)
    private String info;
    private boolean archived;
    private boolean reverse;

    public Route(RouteBody routeBody) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.setShip(new Ship(routeBody.getShipId()));
        this.name = routeBody.getName();
        this.setRoutePoints(createRoutePoints(routeBody.getFromId(), routeBody.getToId()));
        this.setInfo(sdf.format(cal.getTime()) + " Судно начало свой путь");
        this.amountOfFuel = routeBody.getAmountOfFuel();
    }

    private List<Port> createRoutePoints(Long fromId, Long toId) {
        List<Port> list = CustomPorts.getAll().stream().map(CustomPorts::getPort).collect(toList());
        if (isReverse(fromId, toId, list)) {
            list = Lists.reverse(list);
            this.setReverse(true);
        }
        int start = 0, end = 0;
        for (int i = 0 ; i< list.size();i++) {
            if (fromId.equals(list.get(i).getId())) {
                start = i;
            } else if (toId.equals(list.get(i).getId())) {
                end = i;
            }
        }
        return list.subList(start, end+1);
    }

    private Boolean isReverse(Long fromId, Long toId, List<Port> list) {
        for (Port port : list) {
            if (fromId.equals(port.getId())) {
                return false;
            } else if (toId.equals(port.getId())) {
                return true;
            }
        }
        return null;
    }
}
