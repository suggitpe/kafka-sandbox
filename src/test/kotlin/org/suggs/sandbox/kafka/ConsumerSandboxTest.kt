package org.suggs.sandbox.kafka

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.serialization.LongDeserializer
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.suggs.sandbox.kafka.KafkaConsumerBuilder.Companion.aKafkaConsumer
import org.suggs.sandbox.kafka.domain.Person
import org.suggs.sandbox.kafka.domain.PersonDeserializer
import java.time.Duration

class ConsumerSandboxTest {

    private val log = LoggerFactory.getLogger(ConsumerSandboxTest::class.java)
    private val topic = "persons"
    private val kafkaUrl = "localhost:9092"
    private val consumer: Consumer<Long, Person> = aKafkaConsumer()
            .connectedTo(kafkaUrl)
            .knownAs("TestConsumer")
            .usingKeyDeserializer(LongDeserializer::class.java.canonicalName)
            .usingValueDeserializer(PersonDeserializer::class.java.canonicalName)
            .build()

    @Test fun `reads messages from a topic`() {
        consumer.subscribe(listOf(topic))
        while (true) {
            val records = consumer.poll(Duration.ofSeconds(3))
            log.info("Received ${records.count()} messages")
            records.forEach { log.info("Received an event with id[${it.key()}] and data: ${it.value()}") }
        }
    }
}