package org.suggs.sandbox.kafka

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.suggs.sandbox.kafka.KafkaConsumerBuilder.Companion.aKafkaConsumer
import org.suggs.sandbox.kafka.KafkaProducerBuilder.Companion.aKafkaProducer
import java.time.Duration.ofMillis
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class KafkaConnectivityTest {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val topic = "test-topic"
    private val kafkaUrl = "localhost:9092"
    private lateinit var producer: Producer<Long, String>
    private lateinit var consumer: Consumer<Long, String>

    @BeforeEach
    fun `set up test`() {
        producer = aKafkaProducer()
                .connectedTo(kafkaUrl)
                .knownAs("TestProducer")
                .usingKeySerializer(LongSerializer::class.java.canonicalName)
                .usingValueSerializer(StringSerializer::class.java.canonicalName)
                .build()

        consumer = aKafkaConsumer()
                .connectedTo(kafkaUrl)
                .knownAs("TestConsumer")
                .usingKeyDeserializer(LongDeserializer::class.java.canonicalName)
                .usingValueDeserializer(StringDeserializer::class.java.canonicalName)
                .build()
    }

    @Test fun `connects to broker and writes a message synchronously`() {
        val metadata = producer.send(ProducerRecord(topic, 1L, "Foo")).get()
        log.debug(metadata.toString())
    }

    @Test fun `connects to broker and writes a message asynchronously`() {

        fun manageResponse(metadata: RecordMetadata, latch: CountDownLatch) {
            log.debug("""metadata is $metadata""")
            latch.countDown()
        }

        val latch = CountDownLatch(1)
        producer.send(ProducerRecord(topic, 1, "Foo")) { metadata, exception -> manageResponse(metadata, latch) }
        latch.await(25, TimeUnit.SECONDS)
    }

    @Test fun `connects to broker and consumes all messages`() {
        consumer.subscribe(listOf(topic))
        while (true) {
            log.debug("Polling")
            val records = consumer.poll(ofMillis(1000))
            records.forEach { log.debug(it.toString()) }
            consumer.commitAsync()
        }
        consumer.close()
    }


}