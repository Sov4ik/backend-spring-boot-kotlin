package com.bookshop.response

import org.springframework.security.core.GrantedAuthority

class SuccessfulSigninResponse(var username: String?, val authorities: Collection<GrantedAuthority>) {

}
