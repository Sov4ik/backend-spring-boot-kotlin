package com.bookshop.controller

import com.bookshop.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/book")
class BookController{

    @Autowired
    lateinit var bookService: BookService

    @GetMapping("/all")
    fun getAllBooks(): ResponseEntity<*> {
        return bookService.getAllBooks()
    }

    @GetMapping("/{title}")
    @PreAuthorize("hasRole('USER')")
    fun userPanel(@PathVariable("title") title: String): ResponseEntity<*> {
        return bookService.getBookByTitle(title)
    }
}
