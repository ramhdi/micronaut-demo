package com.example.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/health")
class HealthController {
    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun health(): Map<String, String> {
        return mapOf("status" to "UP")
    }
}