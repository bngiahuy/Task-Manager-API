package com.example.taskmanagerapi.config

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

object JsonConverter {
    @Throws(JsonProcessingException::class)
    fun convertObjectToJson(obj: Any?): String? {
        return if (obj == null) {
            null
        } else {
            val mapper = ObjectMapper()
            mapper.writeValueAsString(obj)
        }
    }
}