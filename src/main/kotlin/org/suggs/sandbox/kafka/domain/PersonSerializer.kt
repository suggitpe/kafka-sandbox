package org.suggs.sandbox.kafka.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class PersonSerializer : Serializer<Person> {

    private val log = LoggerFactory.getLogger(PersonSerializer::class.java)
    private val mapper = ObjectMapper().apply {
        registerKotlinModule()
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = StdDateFormat()
    }

    override fun serialize(topic: String, data: Person?): ByteArray? {
        log.info("Serializing $data for topic $topic")
        return when (data) {
            null -> null
            else -> mapper.writeValueAsBytes(data)
        }
    }

    override fun close() {}
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}
}