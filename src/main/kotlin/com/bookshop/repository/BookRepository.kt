package com.bookshop.repository

import com.bookshop.jpa.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByTitle(title: String): Optional<Book>
}
