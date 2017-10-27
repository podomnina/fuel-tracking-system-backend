package com.startup.controller;

import com.startup.model.entities.Port;
import com.startup.model.requestbody.PortBody;
import com.startup.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.startup.controller.ApiPath.ID_PATH;
import static com.startup.controller.ApiPath.PORT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PortController {
    private final PortService portService;

    @Autowired
    public PortController(PortService portService) {
        this.portService = portService;
    }

    @RequestMapping(value = PORT_PATH, method = POST)
    public ResponseEntity<Long> createPort(
            @RequestBody
                    PortBody portBody) {
        return new ResponseEntity<>(portService.createPort(portBody), HttpStatus.OK);
    }

    @RequestMapping(value = PORT_PATH, method = GET)
    public ResponseEntity<List<Port>> getAllPort() {
        return new ResponseEntity<>(portService.getAllPort(), HttpStatus.OK);
    }

    @RequestMapping(value = PORT_PATH + ID_PATH, method = GET)
    public ResponseEntity<Port> getPort(@PathVariable Long id) {
        return new ResponseEntity<>(portService.getPort(id), HttpStatus.OK);
    }

    @RequestMapping(value = PORT_PATH, method = DELETE)
    public ResponseEntity<Object> deletePort(@PathVariable Long id) {
        portService.deletePort(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}