package jooyung.com.joomoney_api.auth.controller

import jooyung.com.joomoney_api.auth.dto.request.EmailVerifyValidateRequest
import jooyung.com.joomoney_api.auth.dto.request.SignUpRequestDto
import jooyung.com.joomoney_api.auth.dto.response.DuplicateResponse
import jooyung.com.joomoney_api.auth.dto.response.EmailVerifyValidateResponse
import jooyung.com.joomoney_api.auth.dto.response.SignUpResponseDto
import jooyung.com.joomoney_api.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val authService: AuthService
){
    @PostMapping("/email/verify/request")
    fun emailVerifyRequest(@RequestParam email: String): ResponseEntity<Void> {
        return authService.emailVerifyRequest(email)
    }

    @PostMapping("/email/verify/validate")
    fun emailVerifyValidate(@RequestBody emailVerifyValidateRequest: EmailVerifyValidateRequest): ResponseEntity<EmailVerifyValidateResponse> {
        return authService.emailVerifyValidate(emailVerifyValidateRequest)
    }

//    @PostMapping("/sign-up")
//    fun signUp(@RequestBody signUpRequestDto: SignUpRequestDto) : ResponseEntity<SignUpResponseDto> {
//        return authService.signUp(signUpRequestDto)
//    }

    @PostMapping("/duplicate/user-id")
    fun checkDuplicateUserId(@RequestParam userId: String): ResponseEntity<DuplicateResponse> {
        return authService.checkDuplicateUserId(userId)
    }

    @PostMapping("/duplicate/email")
    fun checkDuplicateEmail(@RequestParam email: String): ResponseEntity<DuplicateResponse> {
        return authService.checkDuplicateEmail(email)
    }
}