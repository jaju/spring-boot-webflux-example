package org.msync.webflux.config

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import java.util.logging.Level
import java.util.logging.Logger


@ReadingConverter
class JsonToMapConverter(val mapper: ObjectMapper): Converter<Json, Map<String, Any>> {
    override fun convert(source: Json): Map<String, Any>? {
//        TODO("Not yet implemented")
        return mapper.readValue(source.asString(), object: TypeReference<Map<String, Any>>() {})
    }
}

@WritingConverter
class MapToJsonConverter(val mapper: ObjectMapper): Converter<Map<String, Any>, Json> {
    override fun convert(source: Map<String, Any>): Json? {
//        TODO("Not yet implemented")
        return Json.of(mapper.writeValueAsString(source))
    }
}

@Configuration
@EnableR2dbcRepositories("org.msync.webflux")
class DBConfig(val r2dbcProps: R2dbcProperties, val mapper: ObjectMapper): AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory {
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

    override fun getCustomConverters(): List<Any> {
        val l = PostgresDialect.INSTANCE.converters
        l.addAll(listOf(JsonToMapConverter(mapper), MapToJsonConverter(mapper)))
        return l.toList()
    }

    @Bean
    override fun r2dbcCustomConversions() : R2dbcCustomConversions {
        println("*********************************************************************************************")
        val customConverters = listOf(JsonToMapConverter(mapper), MapToJsonConverter(mapper))
        return R2dbcCustomConversions(storeConversions, customConverters)
    }

}