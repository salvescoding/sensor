package com.sensor.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class SensorDTOApiControllerTest {

    @Autowired
    private WebTestClient webTestClient ;


    @Test
    void shouldReturnNoContentWhenNewSensorEventsComeWithAnEmptyBody() {
        SensorsDTO sensors = new SensorsDTO(List.of(
                new SensorDTO(1, 23.0, 59.2, 32.3, "2003-06-30T20:00:00")
        ));

        webTestClient
                .post()
                .uri("/sensors")
                .body(Mono.just(sensors), SensorsDTO.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(SensorsDTO.class)
                .returnResult();


    }
}