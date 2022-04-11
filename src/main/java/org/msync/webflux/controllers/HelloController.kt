package org.msync.webflux.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping(*["", "/"], consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun sayHelloText(): Mono<String> = Mono.just("Hello")

    @GetMapping(*["", "/"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sayHelloJson(): Mono<Map<String,String>> {
        return Mono.just(mapOf("greeting" to "Hello"))
    }

}