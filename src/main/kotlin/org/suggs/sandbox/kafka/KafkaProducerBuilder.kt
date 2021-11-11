package org.suggs.sandbox.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.*

class KafkaProducerBuilder {

    private val properties = Properties()

    init {
        properties[ProducerConfig.ACKS_CONFIG] = "all"
        properties[ProducerConfig.RETRIES_CONFIG] = 0
        properties[ProducerConfig.LINGER_MS_CONFIG] = 1
    }

    companion object {
        fun aKafkaProducer(): KafkaProducerBuilder {
            return KafkaProducerBuilder()
        }
    }

    fun connectedTo(kafkaUrl: String): KafkaProducerBuilder {
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaUrl
        return this
    }

    fun knownAs(clientName: String): KafkaProducerBuilder {
        properties[ProducerConfig.CLIENT_ID_CONFIG] = clientName
        return this
    }

    fun usingKeySerializer(deserializerName: String): KafkaProducerBuilder {
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = deserializerName
        return this
    }

    fun usingValueSerializer(deserializerName: String): KafkaProducerBuilder {
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = deserializerName
        return this
    }

    fun <K, V> build(): Producer<K, V> {
        return KafkaProducer(properties)
    }
}
