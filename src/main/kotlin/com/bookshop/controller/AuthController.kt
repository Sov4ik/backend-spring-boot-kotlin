package com.bookshop.controller

import com.bookshop.response.ResponseMessage
import com.bookshop.response.loginUser
import com.bookshop.response.newUser
import com.bookshop.service.AuthService
import com.bookshop.service.UserDetailsService
import com.bookshop.service.UserDetailsServiceImpl.Companion.TOKEN_EXPIRED
import com.bookshop.service.UserDetailsServiceImpl.Companion.TOKEN_INVALID
import com.bookshop.service.UserDetailsServiceImpl.Companion.TOKEN_VALID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.io.UnsupportedEncodingException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    lateinit var authService : AuthService

    @Autowired
    lateinit var userService: UserDetailsService


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: loginUser, response: HttpServletResponse): ResponseEntity<*> {
        return authService.authenticationUser(loginRequest, response)
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody newUser: newUser): ResponseEntity<*> {
        return authService.registrationUser(newUser)
    }

    @PostMapping("/registrationConfirm")
    @CrossOrigin(origins = ["*"])
    @Throws(UnsupportedEncodingException::class)
    fun confirmRegistration(request: HttpServletRequest, model: Model, @RequestParam("token") token: String): ResponseEntity<*> {

        when(userService.validateVerificationToken(token)) {
            TOKEN_VALID -> return ResponseEntity.ok(ResponseMessage("Registration confirmed"))
            TOKEN_INVALID -> return ResponseEntity(ResponseMessage("Token is invalid!"), HttpStatus.BAD_REQUEST)
            TOKEN_EXPIRED -> return ResponseEntity(ResponseMessage("Token is invalid!"), HttpStatus.UNAUTHORIZED)
        }

        return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"), HttpStatus.SERVICE_UNAVAILABLE)
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<*> {
       return authService.logout(response)
    }



}
