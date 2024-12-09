package org.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketing.entity.SystemConfig;
import org.ticketing.service.SystemManagerService;
// Remove TicketPool related code since it's causing errors
// import org.ticketing.service.TicketPoolService;

@RestController
@RequestMapping("/api/ticket-system")
public class TicketSystemController {
    @Autowired
    private SystemManagerService systemManager;

    // Remove this for now until we fix TicketPoolService
    // @Autowired
    // private TicketPoolService ticketPool;

    @PostMapping("/initialize")
    public void initializeSystem(@RequestBody SystemConfig config) {
        systemManager.initializeSystem(config);
    }

    @PostMapping("/start")
    public void startSystem() {
        systemManager.startSystem();
    }

    @PostMapping("/stop")
    public void stopSystem() {
        systemManager.stopSystem();
    }
}