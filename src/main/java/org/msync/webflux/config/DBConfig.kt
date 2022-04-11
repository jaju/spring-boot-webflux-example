package org.msync.webflux.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.logging.Level
import java.util.logging.Logger

@Configuration
class DBConfig {

    @Bean
    fun connectionFactory(r2dbcProps: R2dbcProperties): ConnectionFactory {

        val options = ConnectionFactoryOptions.parse(r2dbcProps.url)
        Logger.getLogger(this.javaClass.name).log(Level.FINE, "Parsed options = $options")
        return ConnectionFactories.get(options)

        // OR
        // Note: Also see https://github.com/spring-projects/spring-boot/issues/26977
        // return ConnectionFactoryBuilder.withUrl(r2dbcProps.url).build()


        // OR something like the following for DB-specific explicit setup
        //  return PostgresqlConnectionFactory(
        //      PostgresqlConnectionConfiguration.builder()
        //          .host(c.properties.getOrDefault("host", "localhost"))
        //          .port(c.properties.getOrDefault("port", "5432").toInt())
        //          .database(c.name)
        //          .username(c.username)
        //          .password("")
        //          .build()
        //  )
    }

}