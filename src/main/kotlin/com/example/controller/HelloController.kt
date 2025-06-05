package com.example.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Produces

@Controller("/hello")
class HelloController {

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "Hello World!"
    }

    @Get("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(name: String): String {
        return "Hello $name!"
    }

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun create(@Body message: Message): Map<String, String> {
        return mapOf("message" to "Created message: ${message.text}")
    }

    @Put("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun update(id: Long, @Body message: Message): Map<String, String> {
        return mapOf("message" to "Updated message $id: ${message.text}")
    }

    @Delete("/{id}")
    @Status(io.micronaut.http.HttpStatus.NO_CONTENT)
    fun delete(id: Long) {

    }
}

data class Message(val text: String)