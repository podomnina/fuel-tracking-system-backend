package com.startup.model.entities;

import com.startup.model.requestbody.PortBody;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Coordinates coordinates;

    public Port(PortBody portBody) {
        this.name = portBody.getName() != null ? portBody.getName() : this.name;
        this.coordinates = portBody.getCoordinates() != null ? portBody.getCoordinates() : this.getCoordinates();
    }

    public Port(Long id) {
        this.id = id;
    }
}
