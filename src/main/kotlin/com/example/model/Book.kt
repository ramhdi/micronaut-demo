package com.example.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Introspected
@Schema(name = "Book", description = "Book resource representation")
data class Book(
    @field:Schema(description = "Unique identifier of the book", example = "1", required = false)
    @JsonProperty("id")
    var id: Long? = null,

    @field:Schema(description = "Title of the book", example = "The Lord of the Rings")
    @field:NotBlank(message = "Title cannot be blank")
    @field:NotNull(message = "Title is required")
    @JsonProperty("title")
    val title: String,

    @field:Schema(description = "Author of the book", example = "J.R.R. Tolkien")
    @field:NotBlank(message = "Author cannot be blank")
    @field:NotNull(message = "Author is required")
    @JsonProperty("author")
    val author: String,

    @field:Schema(description = "Number of pages", example = "423")
    @field:Positive(message = "Pages must be a positive number")
    @field:NotNull(message = "Pages count is required")
    @JsonProperty("pages")
    val pages: Int
)

@Schema(name = "BookCreateRequest")
data class BookCreateRequest(
    @field:Schema()
    @field:NotBlank(message = "Title cannot be blank")
    @field:NotNull(message = "Title is required")
    @JsonProperty("title")
    val title: String,

    @field:Schema()
    @field:NotBlank(message = "Author cannot be blank")
    @field:NotNull(message = "Author is required")
    @JsonProperty("author")
    val author: String,

    @field:Schema()
    @field:Positive(message = "Pages must be a positive number")
    @field:NotNull(message = "Pages count is required")
    @JsonProperty("pages")
    val pages: Int
)

@Schema(name = "BookUpdateRequest")
data class BookUpdateRequest(
    @field:Schema()
    @field:NotBlank(message = "Title cannot be blank")
    @field:NotNull(message = "Title is required")
    @JsonProperty("title")
    val title: String,

    @field:Schema()
    @field:NotBlank(message = "Author cannot be blank")
    @field:NotNull(message = "Author is required")
    @JsonProperty("author")
    val author: String,

    @field:Schema()
    @field:Positive(message = "Pages must be a positive number")
    @field:NotNull(message = "Pages count is required")
    @JsonProperty("pages")
    val pages: Int
)