package org.labcabrera.sample.loki.interfaces.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(HelloWorldController.class)
class HelloWorldControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnHelloWorld() {
        webTestClient.get()
            .uri("/api/v1/counters/")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Hello, World!");
    }

    @Test
    void shouldIncrementCounterReactively() {
        // First increment
        webTestClient.post()
            .uri("/api/v1/counters/reactive-test")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Integer.class)
            .isEqualTo(1);

        // Second increment
        webTestClient.post()
            .uri("/api/v1/counters/reactive-test")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Integer.class)
            .isEqualTo(2);
    }

    @Test
    void shouldHandleMultipleDifferentCounters() {
        // Test counter A
        webTestClient.post()
            .uri("/api/v1/counters/counter-a")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Integer.class)
            .isEqualTo(1);

        // Test counter B
        webTestClient.post()
            .uri("/api/v1/counters/counter-b")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Integer.class)
            .isEqualTo(1);

        // Increment counter A again
        webTestClient.post()
            .uri("/api/v1/counters/counter-a")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Integer.class)
            .isEqualTo(2);
    }
}