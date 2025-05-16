package com.example.entity

import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@Table(name = "books")
@Introspected
@Schema(name = "Book", description = "Book entity")
data class BookEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(description = "Unique identifier", example = "1")
    val id: Long? = null,

    @field:NotBlank
    @field:Schema(description = "Title", example = "The Lord of the Rings")
    val title: String,

    @field:NotBlank
    @field:Schema(description = "Author", example = "J.R.R. Tolkien")
    val author: String,

    @field:NotNull
    @field:Positive
    @field:Schema(description = "Page count", example = "423")
    val pages: Int
)