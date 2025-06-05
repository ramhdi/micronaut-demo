package com.example.service

import com.example.entity.BookEntity
import com.example.model.Book
import com.example.model.BookCreateRequest
import com.example.model.BookUpdateRequest
import com.example.repository.BookRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Singleton
class BookService(private val bookRepository: BookRepository) {

    fun findAll(): Mono<List<Book>> {
        return bookRepository.findAll()
            .collectList()
            .map { entities -> entities.map { it.toModel() } }
    }

    fun findById(id: Long): Mono<Book?> {
        return bookRepository.findById(id)
            .map { it.toModel() }
    }

    fun existsById(id: Long): Mono<Boolean> {
        return bookRepository.existsById(id)
    }

    fun existsByTitleAndAuthor(title: String, author: String): Mono<Boolean> {
        return bookRepository.existsByTitleAndAuthor(title, author)
    }

    fun create(request: BookCreateRequest): Mono<Book> {
        return existsByTitleAndAuthor(request.title, request.author)
            .flatMap { exists ->
                if (exists) {
                    Mono.error(BookAlreadyExistsException("Book with title '${request.title}' by '${request.author}' already exists"))
                } else {
                    val entity = BookEntity(
                        id = null,
                        title = request.title,
                        author = request.author,
                        pages = request.pages
                    )
                    bookRepository.save(entity).map { it.toModel() }
                }
            }
    }

    fun update(id: Long, request: BookUpdateRequest): Mono<Book> {
        return existsById(id)
            .flatMap { exists ->
                if (!exists) {
                    Mono.error(BookNotFoundException("Book with id $id not found"))
                } else {
                    bookRepository.update(id, request.title, request.author, request.pages)
                        .then(bookRepository.findById(id))
                        .map { it.toModel() }
                }
            }
    }

    fun deleteById(id: Long): Mono<Void> {
        return existsById(id)
            .flatMap { exists ->
                if (!exists) {
                    Mono.error(BookNotFoundException("Book with id $id not found"))
                } else {
                    bookRepository.deleteById(id).then()
                }
            }
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