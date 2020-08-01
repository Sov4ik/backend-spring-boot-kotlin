package com.bookshop.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

object LoginUtils {
    private val OBJECT_MAPPER = ObjectMapper()

    @Throws(Exception::class)
    fun getTokenForLogin(username: String, password: String, mockMvc: MockMvc): String? {
        val content = mockMvc.post("/api/auth/signin") {
            contentType = (MediaType.APPLICATION_JSON)
            content = ("{\"password\": \"$password\", \"username\": \"$username\"}")
        }
                .andReturn()
                .response
                .getCookie("authCookie")

        return content?.value
    }

    private class AuthenticationResponse(var accessToken: String)
}
