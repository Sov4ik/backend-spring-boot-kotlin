package com.bookshop.rest

import com.bookshop.util.AbstractControllerTest
import com.bookshop.util.LoginUtils.getTokenForLogin
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class AdminProtectedControllerTest : AbstractControllerTest() {

    @Test
    fun getAdminProtectedEndpointForUser(){
        val token: String? = getTokenForLogin("aa", "123456", mockMvc)

        mockMvc.get("/api/test/admin"){
            contentType = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer $token")
        }.andExpect{
            status().is5xxServerError
        }
    }

    @Test
   fun checkAccessForUser() {
        val token: String? = getTokenForLogin("aa", "123456", mockMvc)

        mockMvc.get("/api/test/user") {
            contentType = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer $token")
        }.andExpect {
            status().isOk }
    }

    @Test
    fun getAdminProtectedEndpointForAdmin() {
        val token: String? = getTokenForLogin("aa", "123456", mockMvc)

        mockMvc.get("/api/test/admin") {
            contentType = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer $token")
        }.andExpect {
            status().isOk
        }
    }

    @Test
    fun getAdminProtectedEndpointForAnonymous(){
        mockMvc.get("/api/test/admin") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status().isOk}
    }
}
