package org.msync.webflux.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class HelloControllerSpec {

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun `returns a greeting in plain text`() {
        client
            .get()
            .uri("/hello")
            .header("Content-Type", "text/plain")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(String::class.java)
            .isEqualTo("Hello")
    }


    @Test
    fun `returns a greeting in JSON`() {
        client
            .get()
            .uri("/hello")
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .jsonPath("greeting")
            .isEqualTo("Hello")
    }

}
