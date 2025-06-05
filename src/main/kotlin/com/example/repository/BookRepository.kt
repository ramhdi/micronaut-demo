package com.example.repository

import com.example.entity.BookEntity
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import kotlinx.coroutines.flow.Flow

@Repository
interface BookRepository : CoroutineCrudRepository<BookEntity, Long> {

    fun findByTitleContains(title: String): Flow<BookEntity>

    fun findByAuthorContains(author: String): Flow<BookEntity>

    suspend fun findByTitleAndAuthor(title: String, author: String): BookEntity?

    suspend fun existsByTitleAndAuthor(title: String, author: String): Boolean

    suspend fun update(
        @NonNull @NotNull @Id id: Long,
        @NonNull @NotBlank title: String,
        @NonNull @NotBlank author: String,
        @NonNull @Positive pages: Int
    ): Long
}