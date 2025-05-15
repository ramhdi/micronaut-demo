package com.example.controller

import com.example.model.Book
import com.example.model.BookCreateRequest
import com.example.model.BookUpdateRequest
import com.example.service.BookAlreadyExistsException
import com.example.service.BookNotFoundException
import com.example.service.BookService
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Error
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link
import jakarta.inject.Inject

@Controller("/books")
class BookController(@Inject private val bookService: BookService) {

    @Get(uri = "/", produces = [MediaType.APPLICATION_JSON])
    fun getAll(): HttpResponse<List<Book>> {
        return HttpResponse.ok(bookService.findAll())
    }

    @Get(uri = "/{id}", produces = [MediaType.APPLICATION_JSON])
    fun getById(id: Long): HttpResponse<Book> {
        return bookService.findById(id)?.let {
            HttpResponse.ok(it)
        } ?: HttpResponse.notFound()
    }

    @Post(uri = "/", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun create(@Body request: BookCreateRequest): HttpResponse<Book> {
        try {
            val savedBook = bookService.create(request)
            return HttpResponse.created(savedBook)
                .header("Location", "/books/${savedBook.id}")
        } catch (e: BookAlreadyExistsException) {
            return HttpResponse.status<Book>(HttpStatus.CONFLICT).body(null)
        }
    }

    @Put(uri = "/{id}", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun update(id: Long, @Body request: BookUpdateRequest): HttpResponse<Book> {
        return try {
            val updatedBook = bookService.update(id, request)
            HttpResponse.ok(updatedBook)
        } catch (e: BookNotFoundException) {
            HttpResponse.notFound()
        }
    }

    @Delete(uri = "/{id}")
    fun delete(id: Long): HttpResponse<Void> {
        return try {
            bookService.deleteById(id)
            HttpResponse.noContent()
        } catch (e: BookNotFoundException) {
            HttpResponse.notFound()
        }
    }

    @Error
    fun onSqlConstraintViolation(request: HttpRequest<*>, e: Exception): HttpResponse<JsonError> {
        val error = JsonError("Bad Request: ${e.message}")
            .link(Link.SELF, Link.of(request.uri))
        return HttpResponse.badRequest<JsonError>().body(error)
    }
}