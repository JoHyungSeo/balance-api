package jooyung.com.joomoney_api.auth.dto.request

data class EmailVerifyValidateRequest (
    val email: String,
    val code: String
)