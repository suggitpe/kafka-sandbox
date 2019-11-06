package org.suggs.sandbox.kafka.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

class PersonDeserializer : Deserializer<Person> {

    private val log = LoggerFactory.getLogger(PersonSerializer::class.java)
    private val mapper = ObjectMapper().apply {
        registerKotlinModule()
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = StdDateFormat()
    }

    override fun deserialize(topic: String, data: ByteArray?): Person? {
        return when (data) {
            null -> null
            else -> mapper.readValue(data, Person::class.java)
        }
    }

    override fun close() {}
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}
}