package com.bookshop.security

import com.bookshop.util.AbstractControllerTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthenticationControllerTest : AbstractControllerTest() {

    @Test
    @Throws(Exception::class)
    fun successfulAuthenticationWithUser() {
        mockMvc.post("/api/auth/signin") {
            contentType = MediaType.APPLICATION_JSON
            content = ("{\"password\": \"123456\", \"username\": \"aa\"}")
        }
                .andExpect { status { isOk } }
    }

    @Test
    @Throws(Exception::class)
    fun successfulAuthenticationWithAdmin() {
        mockMvc.post("/api/auth/signin") {
            contentType = (MediaType.APPLICATION_JSON)
            content = ("{\"password\": \"123456\", \"username\": \"aa\"}")
        }.andExpect {
            status { isOk }
        }
    }

    @Test
    @Throws(Exception::class)
    fun unsuccessfulAuthenticationWithWrongPassword() {
        mockMvc.post("/api/auth/signin") {
            contentType = (MediaType.APPLICATION_JSON)
            content = ("{\"password\": \"aaaa\", \"username\": \"baaaaaaa\"}")
        }
                .andExpect {
                    status { isBadRequest }
                }
    }

    @Test
    @Throws(Exception::class)
    fun unsuccessfulAuthenticationWithNotExistingUser() {
        mockMvc.post("/api/auth/signin") {
            contentType = (MediaType.APPLICATION_JSON)
            content = ("{\"password\": \"anypassword\", \"username\": \"notexisting\"}")
        }
                .andExpect {
                    status { isBadRequest }
                }
    }
}
