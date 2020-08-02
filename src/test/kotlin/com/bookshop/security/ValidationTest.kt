package com.bookshop.security

import com.bookshop.util.AbstractControllerTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional


class ValidationTest : AbstractControllerTest() {

    @Test
    @Transactional
    @Rollback
    @Disabled
    @Throws(Exception::class)
    fun registrationWithEmptyUsernameAndPassword() {
        mockMvc.post("/api/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ("{\"username\": \"\", \"password\": \"\", \"email\": \"email@email.ru\"}")
        }.andExpect{
            status { isBadRequest }
        }
    }

    @Test
    @Transactional
    @Rollback
    @Disabled
    @Throws(Exception::class)
    fun registrationWithInvalidUsername() {
        mockMvc.post("/api/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ("{\"username\": \"u53rnA'>3\", \"password\": \"password\", \"email\": \"email@email.ru\"}")
        }.andExpect {
            status { isBadRequest }
        }
    }

    @Test
    @Disabled
    @Throws(Exception::class)
    fun registrationWithInvalidPassword() {
        mockMvc.post("/api/auth/signup") {
            contentType = (MediaType.APPLICATION_JSON)
            content = ("{\"username\": \"username\", \"password\": \"pa55w0[d\", \"email\": \"email@email.ru\"}")
        }.andExpect {
            status { isBadRequest }
        }
    }

    @Test
    @Throws(Exception::class)
    fun loginWithEmptyUsernameAndPassword() {
        mockMvc.post("/api/auth/signin") {
            contentType = MediaType.APPLICATION_JSON
            content = ("{\"username\": \"\", \"password\": \"\"}")
        }.andExpect { status { isBadRequest } }
    }

    @Test
    @Throws(Exception::class)
    fun loginWithInvalidUsername() {
        mockMvc.post("/api/auth/signin") {
            contentType = MediaType.APPLICATION_JSON
            content = ("{\"username\": \"kjhasd'}\", \"password\": \"\"}")
        }.andExpect { status { isBadRequest } }
    }

    @Test
    @Throws(Exception::class)
    fun loginWithInvalidPassword() {
        mockMvc.post("/api/auth/signin") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\"username\": \"\", \"password\": \"{123}\"}"
        }.andExpect { status { isBadRequest } }
    }
}
