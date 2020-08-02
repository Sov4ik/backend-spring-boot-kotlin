package com.bookshop.jpa

import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = 0,

        @Column(name="title")
        var title: String?=null,

        @Column(name="bookName")
        var bookName: String?=null,

        @Column(name="imageUrl")
        var imageUrl: String?=null,

        @Column(name="inStock")
        var inStock: Int = 0

)
