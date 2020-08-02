package com.bookshop.service

import com.bookshop.jpa.User
import com.bookshop.jwt.JwtProvider
import com.bookshop.repository.RoleRepository
import com.bookshop.repository.UserRepository
import com.bookshop.response.ResponseMessage
import com.bookshop.response.SuccessfulSigninResponse
import com.bookshop.response.loginUser
import com.bookshop.response.newUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class AuthService{

    @Value("\${app.authCookieName}")
    lateinit var authCookieName: String

    @Value("\${app.isCookieSecure}")
    var isCookieSecure: Boolean = true

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider


    fun authenticationUser(loginRequest: loginUser, response: HttpServletResponse): ResponseEntity<*>{
        val userCandidate: Optional<User> = userRepository.findByUsername(loginRequest.username!!)

        if (userCandidate.isPresent) {
            val user: User = userCandidate.get()

            if (!user.enabled) {
                return ResponseEntity(ResponseMessage("Account is not verified yet! Please, follow the link in the confirmation email."),
                        HttpStatus.UNAUTHORIZED)
            }

            return authenticateAndSetCookie(loginRequest, response, user)

        } else {
            return ResponseEntity(ResponseMessage("User not found!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    fun authenticateAndSetCookie(loginRequest: loginUser, response: HttpServletResponse, user: User) : ResponseEntity<*>{
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtProvider.generateJwtToken(user.username!!)

        val cookie = Cookie(authCookieName, jwt)
        cookie.maxAge = jwtProvider.jwtExpiration!!
        cookie.secure = isCookieSecure
        cookie.isHttpOnly = true
        cookie.path = "/"
        response.addCookie(cookie)

        val authorities: List<GrantedAuthority> = user.roles!!.stream().map { role -> SimpleGrantedAuthority(role.name) }.collect(Collectors.toList<GrantedAuthority>())
        return ResponseEntity.ok(SuccessfulSigninResponse(user.username, authorities))
    }

    fun registrationUser(newUser: newUser): ResponseEntity<*>{

        val userCandidate: Optional <User> = userRepository.findByUsername(newUser.username!!)

        if (!userCandidate.isPresent) {
            if (usernameExists(newUser.username!!)) {
                return ResponseEntity(ResponseMessage("Username is already taken!"),
                        HttpStatus.BAD_REQUEST)
            } else if (emailExists(newUser.email!!)) {
                return ResponseEntity(ResponseMessage("Email is already in use!"),
                        HttpStatus.BAD_REQUEST)
            }

            try {
                // Creating user's account
                val user = User(
                        0,
                        newUser.username!!,
                        newUser.email,
                        encoder.encode(newUser.password),
                        false
                )
                user.roles = listOf(roleRepository.findByName("ROLE_USER"))
                val registeredUser = userRepository.save(user)
                /*val emailService = EmailServiceImpl()
                emailService.sendRegistrationConfirmationEmail(registeredUser)*/
            } catch (e: Exception) {
                return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                        HttpStatus.SERVICE_UNAVAILABLE)
            }

            return ResponseEntity(ResponseMessage("Please, follow the link in the confirmation email to complete the registration."), HttpStatus.OK)
        } else {
            return ResponseEntity(ResponseMessage("User already exists!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    fun logout(response: HttpServletResponse): ResponseEntity<*>{
        val cookie = Cookie(authCookieName, null)
        cookie.maxAge = 0
        cookie.secure = isCookieSecure
        cookie.isHttpOnly = true
        cookie.path = "/"
        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseMessage("Successfully logged"))
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByUsername(email).isPresent
    }

    private fun usernameExists(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

}
