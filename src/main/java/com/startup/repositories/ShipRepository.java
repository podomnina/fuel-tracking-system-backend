package com.startup.repositories;

import com.startup.model.entities.Ship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends CrudRepository<Ship, Long> {


}
