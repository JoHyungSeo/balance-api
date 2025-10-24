package jooyung.com.joomoney_api.auth.dto.request

import jooyung.com.joomoney_api.auth.dto.DeviceInformationDto
import java.time.LocalDate

data class SignUpRequestDto (
    val userId: String,
    val name: String,
    val email: String,
    val verificationToken: String,
    val password: String,
    val birthday: LocalDate?,
    val gender: String?,
    val payday: String?,
    val currency: String,
    val language: String,
    val theme: String,
    val deviceInformationDto: DeviceInformationDto
)