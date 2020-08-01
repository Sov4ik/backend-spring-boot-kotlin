package com.bookshop.controller

import com.bookshop.response.ResponseMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class EmailController {

   /* @Value("\${spring.mail.username}")
    lateinit var addressee: String*/

    @GetMapping("/admincontent")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    fun getAdminContent(): String {
        return "Admin's content"
    }

    @GetMapping("/sendSimpleEmail")
    @PreAuthorize("hasRole('USER')")
    fun sendSimpleEmail(): ResponseEntity<*> {
        try {
            //emailService.sendSimpleMessage(addressee, "Simple Email", "Hello! This is simple email")
        } catch (e: Exception) {
            return ResponseEntity(ResponseMessage("Error while sending message"), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ResponseMessage("Email has been sent"), HttpStatus.OK)
    }

    @GetMapping("/sendTemplateEmail")
    @PreAuthorize("hasRole('USER')")
    fun sendTemplateEmail(): ResponseEntity<*> {
        try {
            val params:MutableMap<String, Any> = mutableMapOf()
            // params["addresseeName"] = addressee
            params["signatureImage"] = "https://coderlook.com/wp-content/uploads/2019/07/spring-by-pivotal.png"
            //emailService.sendSimpleMessageUsingTemplate(addressee, "Template Email", "emailTemplate", params)
        } catch (e: Exception) {
            return ResponseEntity(ResponseMessage("Error while sending message"), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ResponseMessage("Email has been sent"), HttpStatus.OK)
    }

    @GetMapping("/sendHtmlEmail")
    @PreAuthorize("hasRole('USER')")
    fun sendHtmlEmail(): ResponseEntity<*> {
        try {
            //emailService.sendHtmlMessage(addressee, "HTML Email", "<h1>Hello!</h1><p>This is HTML email</p>")
        } catch (e: Exception) {
            return ResponseEntity(ResponseMessage("Error while sending message"), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ResponseMessage("Email has been sent"), HttpStatus.OK)
    }
}
