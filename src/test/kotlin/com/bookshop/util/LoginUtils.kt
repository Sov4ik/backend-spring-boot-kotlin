package com.bookshop.util

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

object LoginUtils {

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
}
