package com.example.controller

import com.example.service.BookAlreadyExistsException
import com.example.service.BookNotFoundException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import jakarta.inject.Singleton

@Singleton
class GlobalExceptionHandler {

    @Error(global = true)
    fun handleBookNotFound(request: HttpRequest<*>, ex: BookNotFoundException): HttpResponse<Map<String, String>> {
        return HttpResponse.status<Map<String, String>>(HttpStatus.NOT_FOUND)
            .body(mapOf("error" to (ex.message ?: "Book not found")))
    }

    @Error(global = true)
    fun handleBookAlreadyExists(
        request: HttpRequest<*>,
        ex: BookAlreadyExistsException
    ): HttpResponse<Map<String, String>> {
        return HttpResponse.status<Map<String, String>>(HttpStatus.CONFLICT)
            .body(mapOf("error" to (ex.message ?: "Book already exists")))
    }
}