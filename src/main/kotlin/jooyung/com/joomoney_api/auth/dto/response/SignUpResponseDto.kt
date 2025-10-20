package jooyung.com.joomoney_api.auth.dto.response

import jooyung.com.joomoney_api.auth.dto.AuthUserInformationDto

data class SignUpResponseDto (
    val accessToken: String,
    val refreshToken: String,
    val userInformation: AuthUserInformationDto
)