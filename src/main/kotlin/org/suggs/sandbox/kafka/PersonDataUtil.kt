package org.suggs.sandbox.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.suggs.sandbox.kafka.domain.Person
import java.text.SimpleDateFormat
import java.util.*

class PersonDataUtil {

    companion object {
        val quentin = Person("Quentin", "Corkey", SimpleDateFormat("yyyy-MM-dd").parse("1984-10-26"))
//                "{ \"firstName\":\"Quentin\", \"lastName\":\"Corkery\", \"birthDate\":\"1984-10-26T03:52:14.449+0000\" }"
        val neil = "{ \"firstName\":\"Neil\", \"lastName\":\"Macejkovic\", \"birthDate\":\"1971-08-06T18:03:11.533+0000\" }"
    }

    private val mapper = ObjectMapper().apply {
        registerKotlinModule()
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = StdDateFormat()
    }

    fun buildPersonFrom(json: String): Person {
        return mapper.readValue(json, Person::class.java)
    }

}