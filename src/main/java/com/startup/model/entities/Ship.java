package com.startup.model.entities;

import com.startup.model.requestbody.ShipBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
@Data
@NoArgsConstructor
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float currentLatitude;
    private float currentLongitude;

    public Ship(Long id) {
        this.id = id;
    }

    public void setCurrentCoodrinates(float currentLatitude, float currentLongitude) {
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }

    public Ship(ShipBody shipBody) {
        this.name = shipBody.getName() != null ? shipBody.getName() : this.name;
    }
}
