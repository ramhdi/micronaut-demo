package com.example.service

import com.example.model.Book
import com.example.model.BookCreateRequest
import com.example.model.BookUpdateRequest
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Singleton
class BookService {

    private val books = ConcurrentHashMap<Long, Book>()
    private val counter = AtomicLong(0)

    init {
        // Add some sample data
        saveInitial(Book(null, "Dune", "Frank Herbert", 412))
        saveInitial(Book(null, "Foundation", "Isaac Asimov", 255))
        saveInitial(Book(null, "1984", "George Orwell", 328))
    }

    fun findAll(): List<Book> = books.values.toList().sortedBy { it.id }

    fun findById(id: Long): Book? = books[id]

    fun existsById(id: Long): Boolean = books.containsKey(id)

    fun existsByTitleAndAuthor(title: String, author: String): Boolean {
        return books.values.any {
            it.title.equals(title, ignoreCase = true) &&
                    it.author.equals(author, ignoreCase = true)
        }
    }

    // Only used for initialization
    private fun saveInitial(book: Book): Book {
        val bookId = counter.incrementAndGet()
        val savedBook = book.copy(id = bookId)
        books[bookId] = savedBook
        return savedBook
    }

    fun create(request: BookCreateRequest): Book {
        // Check if book with same title and author already exists
        if (existsByTitleAndAuthor(request.title, request.author)) {
            throw BookAlreadyExistsException("Book with title '${request.title}' by '${request.author}' already exists")
        }

        val bookId = counter.incrementAndGet()
        val newBook = Book(
            id = bookId,
            title = request.title,
            author = request.author,
            pages = request.pages
        )
        books[bookId] = newBook
        return newBook
    }

    fun update(id: Long, request: BookUpdateRequest): Book {
        if (!books.containsKey(id)) {
            throw BookNotFoundException("Book with id $id not found")
        }

        val updatedBook = Book(
            id = id,
            title = request.title,
            author = request.author,
            pages = request.pages
        )
        books[id] = updatedBook
        return updatedBook
    }

    fun deleteById(id: Long): Boolean {
        if (!books.containsKey(id)) {
            throw BookNotFoundException("Book with id $id not found")
        }
        return books.remove(id) != null
    }
}

class BookNotFoundException(message: String) : RuntimeException(message)
class BookAlreadyExistsException(message: String) : RuntimeException(message)