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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject

@Controller("/books")
@Tag(name = "Books", description = "Book Management APIs")
class BookController(@Inject private val bookService: BookService) {

    @Get("/")
    @Operation(summary = "List all books", description = "Returns a list of all available books")
    @ApiResponse(
        responseCode = "200", description = "List of books",
        content = [Content(mediaType = MediaType.APPLICATION_JSON, schema = Schema(implementation = Book::class))]
    )
    fun getAll(): HttpResponse<List<Book>> {
        return HttpResponse.ok(bookService.findAll())
    }

    @Get("/{id}")
    @Operation(summary = "Get book by ID", description = "Returns a book based on its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Book found"),
        ApiResponse(responseCode = "404", description = "Book not found")
    )
    fun getById(
        @Parameter(description = "Book ID", required = true) id: Long
    ): HttpResponse<Book> {
        return bookService.findById(id)?.let {
            HttpResponse.ok(it)
        } ?: HttpResponse.notFound()
    }

    @Post("/")
    @Operation(summary = "Create a book", description = "Creates a new book record")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Book created successfully"),
        ApiResponse(responseCode = "409", description = "Book already exists")
    )
    fun create(@Body request: BookCreateRequest): HttpResponse<Book> {
        try {
            val savedBook = bookService.create(request)
            return HttpResponse.created(savedBook)
                .header("Location", "/books/${savedBook.id}")
        } catch (e: BookAlreadyExistsException) {
            return HttpResponse.status<Book>(HttpStatus.CONFLICT).body(null)
        }
    }

    @Put("/{id}")
    @Operation(summary = "Update a book", description = "Updates an existing book")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Book updated successfully"),
        ApiResponse(responseCode = "404", description = "Book not found")
    )
    fun update(
        @Parameter(description = "Book ID", required = true) id: Long,
        @Body request: BookUpdateRequest
    ): HttpResponse<Book> {
        return try {
            val updatedBook = bookService.update(id, request)
            HttpResponse.ok(updatedBook)
        } catch (e: BookNotFoundException) {
            HttpResponse.notFound()
        }
    }

    @Delete("/{id}")
    @Operation(summary = "Delete a book", description = "Deletes a book by ID")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        ApiResponse(responseCode = "404", description = "Book not found")
    )
    fun delete(
        @Parameter(description = "Book ID", required = true) id: Long
    ): HttpResponse<Void> {
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