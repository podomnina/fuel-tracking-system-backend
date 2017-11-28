package com.startup.model.entities;

import com.startup.model.requestbody.PortBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float latitude;
    private float longitude;

    public Port(PortBody portBody) {
        this.name = portBody.getName() != null ? portBody.getName() : this.name;
        this.latitude = portBody.getLatitude();
        this.longitude = portBody.getLongitude();
    }

    public Port(Long id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Port(Long id) {
        this.id = id;
    }
}
