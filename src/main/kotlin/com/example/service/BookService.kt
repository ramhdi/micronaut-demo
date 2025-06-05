package com.example.service

import com.example.entity.BookEntity
import com.example.model.Book
import com.example.model.BookCreateRequest
import com.example.model.BookUpdateRequest
import com.example.repository.BookRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

@Singleton
class BookService(private val bookRepository: BookRepository) {

    suspend fun findAll(): List<Book> {
        return bookRepository.findAll()
            .map { it.toModel() }
            .toList()
    }

    suspend fun findById(id: Long): Book? {
        return bookRepository.findById(id)?.toModel()
    }

    suspend fun existsById(id: Long): Boolean {
        return bookRepository.existsById(id)
    }

    suspend fun existsByTitleAndAuthor(title: String, author: String): Boolean {
        return bookRepository.existsByTitleAndAuthor(title, author)
    }

    @Throws(BookAlreadyExistsException::class)
    suspend fun create(request: BookCreateRequest): Book {
        val exists = existsByTitleAndAuthor(request.title, request.author)
        if (exists) {
            throw BookAlreadyExistsException("Book with title '${request.title}' by '${request.author}' already exists")
        }

        val entity = BookEntity(
            id = null,
            title = request.title,
            author = request.author,
            pages = request.pages
        )

        val saved = bookRepository.save(entity)
        return saved.toModel()
    }

    @Throws(BookNotFoundException::class)
    suspend fun update(id: Long, request: BookUpdateRequest): Book {
        val exists = existsById(id)
        if (!exists) {
            throw BookNotFoundException("Book with id $id not found")
        }

        bookRepository.update(id, request.title, request.author, request.pages)
        val updated = bookRepository.findById(id)
            ?: throw BookNotFoundException("Book with id $id not found after update")

        return updated.toModel()
    }

    @Throws(BookNotFoundException::class)
    suspend fun deleteById(id: Long) {
        val exists = existsById(id)
        if (!exists) {
            throw BookNotFoundException("Book with id $id not found")
        }

        bookRepository.deleteById(id)
    }

    private fun BookEntity.toModel(): Book {
        return Book(
            id = this.id,
            title = this.title,
            author = this.author,
            pages = this.pages
        )
    }
}

class BookNotFoundException(message: String) : RuntimeException(message)
class BookAlreadyExistsException(message: String) : RuntimeException(message)