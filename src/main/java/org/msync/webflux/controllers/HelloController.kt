package org.msync.webflux.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/hello")
open class HelloController {

    @GetMapping("/")
    fun sayHello(): Mono<Map<String, String>> {
        println("hello")
        return Mono.just(mapOf("greeting" to "Hello"))
    }

}