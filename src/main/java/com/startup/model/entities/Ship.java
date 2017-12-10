package com.startup.model.entities;

import com.startup.model.requestbody.ShipBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
    @ManyToOne
    @JoinColumn(name = "from_port_id")
    private Port currentFrom;
    @ManyToOne
    @JoinColumn(name = "to_port_id")
    private Port currentTo;
    private float currentFuel;

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
