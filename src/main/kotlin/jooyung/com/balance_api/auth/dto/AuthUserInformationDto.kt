package jooyung.com.balance_api.auth.dto

data class AuthUserInformationDto (
    val userSeq: Long,
    val userId: String,
    val name: String,
    val email: String
)