package com.bookshop.repository

import com.bookshop.jpa.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: CrudRepository<Person, Long>
