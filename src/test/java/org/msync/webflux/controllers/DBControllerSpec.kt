package org.msync.webflux.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class DBControllerSpec {
    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun `returns a list of aircrafts`() {
        client
            .get()
            .uri("/db/aircrafts")
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList(Aircraft::class.java)
    }
}