package jooyung.com.joomoney_api.auth.controller

import jooyung.com.joomoney_api.auth.dto.request.SignUpRequestDto
import jooyung.com.joomoney_api.auth.dto.response.DuplicateResponse
import jooyung.com.joomoney_api.auth.dto.response.SignUpResponseDto
import jooyung.com.joomoney_api.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val authService: AuthService
){
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpRequestDto: SignUpRequestDto) : ResponseEntity<SignUpResponseDto> {
        return authService.signUp(signUpRequestDto)
    }

    @PostMapping("/duplicate/email")
    fun checkDuplicateEmail(@RequestParam email: String): ResponseEntity<DuplicateResponse> {
        return authService.checkDuplicateEmail(email)
    }
}