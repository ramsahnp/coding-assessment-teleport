package com.teleport.must.have.skills.one_three.controller;

import com.teleport.must.have.skills.one_three.entity.TimezoneResponse;
import com.teleport.must.have.skills.one_three.kafka.UserEventProducer;
import com.teleport.must.have.skills.one_three.service.TimezoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/timezone")
public class TimezoneController {
    private static final Logger logger = LoggerFactory.getLogger(TimezoneController.class);

    private final TimezoneService timezoneService;

    @Autowired
    public TimezoneController(TimezoneService timezoneService) {
        this.timezoneService = timezoneService;
    }

    @GetMapping("/{zone}")
    public Mono<TimezoneResponse> getTime(@PathVariable String zone) {
        logger.info("zone name=={}", zone);
        return timezoneService.getTimezoneData(zone);
    }
}
