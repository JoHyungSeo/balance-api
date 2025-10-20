package jooyung.com.balance_api.auth.dto.response

import jooyung.com.balance_api.auth.dto.AuthUserInformationDto

data class SignUpResponseDto (
    val accessToken: String,
    val refreshToken: String,
    val userInformation: AuthUserInformationDto
)