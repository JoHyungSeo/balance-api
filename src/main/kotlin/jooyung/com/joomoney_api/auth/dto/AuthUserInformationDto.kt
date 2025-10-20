package jooyung.com.joomoney_api.auth.dto

data class AuthUserInformationDto (
    val userSeq: Long,
    val userId: String,
    val name: String,
    val email: String
)