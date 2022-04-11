package org.msync.webflux.controllers.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class TestDBConfig {

    @Bean
    fun connectionFactoryInitializer(cf: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(cf)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("dbsetup.sql")))
        return initializer
    }

}