package org.msync.webflux

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class App

fun main(args: Array<String>) {
    val app = SpringApplication(App::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    app.run(*args)
}