package com.sensor.api.controller;

import com.sensor.api.domain.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class SensorApiControllerTest {

    @Autowired
    private WebTestClient webTestClient ;


    @Test
    void shouldReturnNoContentWhenNewSensorEventsComeWithAnEmptyBody() {
        SensorsDTO sensors = new SensorsDTO(List.of(
                new Sensor(1, 23.0, 59.2, 32.3,
                        LocalDateTime.of(2022, 11, 9, 19, 0, 0))
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