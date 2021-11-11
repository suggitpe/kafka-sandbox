package org.suggs.sandbox.kafka

import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.LongSerializer
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.suggs.sandbox.kafka.KafkaProducerBuilder.Companion.aKafkaProducer
import org.suggs.sandbox.kafka.domain.Person
import org.suggs.sandbox.kafka.domain.PersonSerializer
import java.text.SimpleDateFormat

class ProducerSandboxTest {

    private val log = LoggerFactory.getLogger(ProducerSandboxTest::class.java)
    private val topic = "persons"
    private val kafkaUrl = "localhost:9092"
    private val aPerson = Person("Quentin", "Corkey", SimpleDateFormat("yyyy-MM-dd").parse("1984-10-26"))
    private val producer: Producer<Long, Person> = aKafkaProducer()
            .connectedTo(kafkaUrl)
            .knownAs("TestProducer")
            .usingKeySerializer(LongSerializer::class.java.canonicalName)
            .usingValueSerializer(PersonSerializer::class.java.canonicalName)
            .build()

    @Test fun `sends a person to kafka`() {
        val futureResult = producer.send(ProducerRecord(topic, 2L, aPerson))
        futureResult.get()
    }
}