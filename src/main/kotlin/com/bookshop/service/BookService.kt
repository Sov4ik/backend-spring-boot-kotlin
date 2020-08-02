package com.bookshop.service

import com.bookshop.repository.BookRepository
import com.bookshop.response.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BookService{

    @Autowired
    lateinit var bookRepository: BookRepository

    fun getAllBooks(): ResponseEntity<*>{
        return ResponseEntity.ok(bookRepository.findAll())
    }

    fun getBookByTitle(title: String): ResponseEntity<*>{
        return if (bookRepository.findByTitle(title).isPresent){
            ResponseEntity.ok(bookRepository.findByTitle(title).get())
        } else ResponseEntity(ResponseMessage("Book not found"), HttpStatus.BAD_REQUEST)
    }
}
