package com.startup.service;


import com.startup.model.entities.Port;
import com.startup.model.requestbody.PortBody;
import com.startup.repositories.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
public class PortService {
    private final PortRepository portRepository;

    @Autowired
    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    public Long createPort(PortBody portBody) {
        final Port port = new Port(portBody);
        portRepository.save(port);
        return port.getId();
    }

    public void deletePort(Long id) {
        portRepository.delete(id);
    }

    public Port getPort(Long id) {
        return portRepository.findOne(id);
    }

    public List<Port> getAllPort() {
        return newArrayList(portRepository.findAll());
    }
}
