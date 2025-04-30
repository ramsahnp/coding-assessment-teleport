package com.teleport.must.have.skills.one_three.service;

import com.teleport.must.have.skills.one_three.entity.TimezoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TimezoneService {

    private final WebClient webClient;

    @Autowired
    public TimezoneService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://worldtimeapi.org").build();
    }

    public Mono<TimezoneResponse> getTimezoneData(String zone) {
        return webClient.get()
                .uri("/api/timezone/{zone}", zone)
                .retrieve()
                .bodyToMono(TimezoneResponse.class);
    }
}
