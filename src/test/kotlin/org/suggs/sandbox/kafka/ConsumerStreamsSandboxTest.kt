package org.suggs.sandbox.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.junit.jupiter.api.Test
import org.suggs.sandbox.kafka.domain.Person
import java.time.ZoneId

class ConsumerStreamsSandboxTest {

    private val streamsBuilder = StreamsBuilder()
    private val topic = "persons"
    private val mapper = ObjectMapper().apply {
        registerKotlinModule()
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = StdDateFormat()
    }

    @Test fun `consumes from kafka as json from a stream`() {

        val personJsonStream = streamsBuilder.stream<String, String>(topic, Consumed.with(Serdes.String(), Serdes.String()))
        val personStream = personJsonStream.mapValues { it -> mapper.readValue(it, Person::class.java) }
        val resStream = personStream.map { _, p ->
            val birthDate = p.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            KeyValue("")
        }
    }
}