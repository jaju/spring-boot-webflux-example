package org.msync.webflux.controllers

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Table("aircrafts")
data class Aircraft(
    val aircraftCode: String,
    val model: String,
    val range: Int
)

@Repository
interface AircraftRepository : ReactiveCrudRepository<Aircraft, String>

@Table("aircrafts_data")
data class AircraftData(
    @Id
    val aircraftCode: String,
    val model: Map<String, Any>,
    val range: Int
)

@Repository
interface AircraftDataRepository : ReactiveCrudRepository<AircraftData, String>

@RestController
@EnableWebFlux
@RequestMapping("/db")
class DBController(
    val aircraftRepository: AircraftRepository,
    val aircraftRawReopository: AircraftDataRepository
) {

    @GetMapping("/aircrafts", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAircrafts(): ResponseEntity<Flux<Aircraft>> {
        return ResponseEntity.ok()
            .header("x-random", java.util.UUID.randomUUID().toString())
            .body(
                aircraftRepository.findAll()
            )
    }

    @PostMapping("/aircrafts", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveAircraft(@RequestBody aircraft: AircraftData): ResponseEntity<Mono<AircraftData>?> {
        return ResponseEntity.ok()
            .body(aircraftRawReopository.save(aircraft))
//            .body(R2dbcEntityTemplate(connectionFactory)
//                .update(AircraftRaw::class.java)
//                .inTable("aircrafts_data")
//                .matching(query(Criteria.where("aircraftCode").isEqual(aircraft.aircraftCode)))
//                .apply(Update.update("model", aircraft.model)))
    }

}