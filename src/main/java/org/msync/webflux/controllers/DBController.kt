package org.msync.webflux.controllers

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Flux


data class Aircraft(
    val aircraftCode: String,
    val model: String,
    val range: Int
)

@Repository
interface AircraftRepository : ReactiveCrudRepository<Aircraft, String> {
    @Query("SELECT * from AIRCRAFTS")
    fun all(): Flux<Aircraft>
}

@RestController
@EnableWebFlux
@RequestMapping("/db")
class DBController(
    val aircraftRepository: AircraftRepository
) {

    @GetMapping("/aircrafts", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAircrafts(): ResponseEntity<Flux<Aircraft>> {
        return ResponseEntity.ok()
            .header("x-random", java.util.UUID.randomUUID().toString())
            .body(
                aircraftRepository
                    .all()
            )
    }

}

