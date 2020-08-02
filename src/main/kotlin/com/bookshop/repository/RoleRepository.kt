package com.bookshop.repository

import com.bookshop.jpa.Role
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(@Param("name") name: String): Role
}
