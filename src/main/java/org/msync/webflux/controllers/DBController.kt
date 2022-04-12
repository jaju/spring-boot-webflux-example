package org.msync.webflux.controllers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update
import org.springframework.data.relational.core.query.isEqual
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.IOException


@Table("aircrafts")
data class Aircraft(
    val aircraftCode: String,
    val model: String,
    val range: Int
)

@Repository
interface AircraftRepository : ReactiveCrudRepository<Aircraft, String>

@Table("aircrafts_data")
data class AircraftRaw(
    val aircraftCode: String,
    val model: Json,
    val range: Int
)

data class AircraftRawHTTP(
    val aircraftCode: String,
    val model: Map<String, Any>,
    val range: Int
)

@RestController
@EnableWebFlux
@RequestMapping("/db")
class DBController(
    val aircraftRepository: AircraftRepository,
    val connectionFactory: ConnectionFactory
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
    fun saveAircraft(@RequestBody aircraft: AircraftRawHTTP): ResponseEntity<Mono<Int>>? {
        println(aircraft.model.javaClass)
        return ResponseEntity.ok()
            .body(R2dbcEntityTemplate(connectionFactory)
                .update(AircraftRaw::class.java)
                .inTable("aircrafts_data")
                .matching(query(Criteria.where("aircraftCode").isEqual(aircraft.aircraftCode)))
                .apply(Update.update("model", aircraft.model)))
    }

}