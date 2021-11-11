package org.suggs.sandbox.kafka

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

class KafkaConsumerBuilder {

    private val properties = Properties()

    companion object {
        fun aKafkaConsumer(): KafkaConsumerBuilder {
            return KafkaConsumerBuilder()
        }
    }

    fun connectedTo(kafkaUrl: String): KafkaConsumerBuilder {
        properties[BOOTSTRAP_SERVERS_CONFIG] = kafkaUrl
        return this
    }

    fun knownAs(clientGroupName: String): KafkaConsumerBuilder {
        properties[GROUP_ID_CONFIG] = clientGroupName
        return this
    }

    fun usingKeyDeserializer(deserializerName: String): KafkaConsumerBuilder {
        properties[KEY_DESERIALIZER_CLASS_CONFIG] = deserializerName
        return this
    }

    fun usingValueDeserializer(deserializerName: String): KafkaConsumerBuilder {
        properties[VALUE_DESERIALIZER_CLASS_CONFIG] = deserializerName
        return this
    }

    fun <K, V> build(): Consumer<K, V> {
        return KafkaConsumer(properties)
    }
}