package com.bookshop.controller

import com.bookshop.response.ResponseMessage
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/test")
class AccessRightsTestController{

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun administrationPanel(): ResponseEntity<*>{
        return ResponseEntity.ok(ResponseMessage("Admin"))
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    fun userPanel(): ResponseEntity<*>{
        return ResponseEntity.ok(ResponseMessage("USER"))
    }
}
