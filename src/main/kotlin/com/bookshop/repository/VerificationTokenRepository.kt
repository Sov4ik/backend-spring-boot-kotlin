package com.bookshop.repository

import com.bookshop.jpa.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}
