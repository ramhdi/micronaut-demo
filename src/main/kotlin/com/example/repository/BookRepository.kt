package com.example.repository

import com.example.entity.BookEntity
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface BookRepository : ReactorPageableRepository<BookEntity, Long> {
    fun findByTitleContains(title: String): Flux<BookEntity>

    fun findByAuthorContains(author: String): Flux<BookEntity>

    fun findByTitleAndAuthor(title: String, author: String): Mono<BookEntity>

    fun existsByTitleAndAuthor(title: String, author: String): Mono<Boolean>

    @NonNull
    fun update(
        @NonNull @NotNull @Id id: Long,
        @NonNull @NotBlank title: String,
        @NonNull @NotBlank author: String,
        @NonNull @Positive pages: Int
    ): Mono<Long>
}