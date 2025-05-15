package com.example.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Introspected
data class Book(
    @JsonProperty("id")
    var id: Long? = null,

    @field:NotBlank(message = "Title cannot be blank")
    @field:NotNull(message = "Title is required")
    @JsonProperty("title")
    val title: String,

    @field:NotBlank(message = "Author cannot be blank")
    @field:NotNull(message = "Author is required")
    @JsonProperty("author")
    val author: String,

    @field:Positive(message = "Pages must be a positive number")
    @field:NotNull(message = "Pages count is required")
    @JsonProperty("pages")
    val pages: Int
)

data class BookCreateRequest(
    @field:NotBlank(message = "Title cannot be blank")
    @field:NotNull(message = "Title is required")
    @JsonProperty("title")
    val title: String,

    @field:NotBlank(message = "Author cannot be blank")
    @field:NotNull(message = "Author is required")
    @JsonProperty("author")
    val author: String,

    @field:Positive(message = "Pages must be a positive number")
    @field:NotNull(message = "Pages count is required")
    @JsonProperty("pages")
    val pages: Int
)

data class BookUpdateRequest(
    @field:NotBlank(message = "Title cannot be blank")
    @field:NotNull(message = "Title is required")
    @JsonProperty("title")
    val title: String,

    @field:NotBlank(message = "Author cannot be blank")
    @field:NotNull(message = "Author is required")
    @JsonProperty("author")
    val author: String,

    @field:Positive(message = "Pages must be a positive number")
    @field:NotNull(message = "Pages count is required")
    @JsonProperty("pages")
    val pages: Int
)