package jooyung.com.joomoney_api.auth.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import jooyung.com.joomoney_api.auth.dto.AuthUserInformationDto
import jooyung.com.joomoney_api.auth.dto.request.SignUpRequestDto
import jooyung.com.joomoney_api.auth.dto.response.DuplicateResponse
import jooyung.com.joomoney_api.auth.dto.response.SignUpResponseDto
import jooyung.com.joomoney_api.auth.entity.*
import jooyung.com.joomoney_api.auth.repository.*
import jooyung.com.joomoney_api.enum.Gender
import jooyung.com.joomoney_api.enum.Theme
import jooyung.com.joomoney_api.exception.ApiException
import jooyung.com.joomoney_api.exception.ResultCode
import jooyung.com.joomoney_api.jwt.JwtProperties
import jooyung.com.joomoney_api.jwt.JwtProvider
import jooyung.com.joomoney_api.util.crypto.Crypto
import jooyung.com.joomoney_api.util.validator.Validator
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class AuthService(
    private val crypto: Crypto,
    private val deviceInformationRepository: DeviceInformationRepository,
    private val jwtProperties: JwtProperties,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val request: HttpServletRequest,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userConfigurationRepository: UserConfigurationRepository,
    private val userRepository: UserRepository,
    private val validator: Validator,
) {
    @Transactional
    fun signUp(signUpRequestDto: SignUpRequestDto): ResponseEntity<SignUpResponseDto> {
        validator.userId(signUpRequestDto.userId)
        validator.name(signUpRequestDto.name)
        validator.email(signUpRequestDto.email)
        validator.password(signUpRequestDto.password)
        validator.birthday(signUpRequestDto.birthday)
        validator.gender(signUpRequestDto.gender)
        validator.payday(signUpRequestDto.payday)
        validator.currency(signUpRequestDto.currency)
        validator.language(signUpRequestDto.language)
        validator.theme(signUpRequestDto.theme)

        if (userRepository.existsByUserId(signUpRequestDto.userId)) {
            throw ApiException(ResultCode.ERR_USER_ID_IS_DUPLICATE)
        }

        if (userRepository.existsByEmail(signUpRequestDto.email)) {
            throw ApiException(ResultCode.ERR_EMAIL_IS_DUPLICATE)
        }

        val encodedPassword = passwordEncoder.encode(signUpRequestDto.password)

        val userInformation = UserInformation().apply {
            userId = signUpRequestDto.userId
            name = signUpRequestDto.name
            email = signUpRequestDto.email
            password = encodedPassword
            birthday = signUpRequestDto.birthday
            gender = Gender.fromName(signUpRequestDto.gender)
            regId = signUpRequestDto.userId
            regDt = LocalDateTime.now()
        }

        val savedUserInformation = userRepository.save(userInformation)

        val userConfiguration = UserConfiguration().apply {
            this.userInformation = savedUserInformation
            this.userSeq = savedUserInformation.userSeq
            this.payday = signUpRequestDto.payday
            this.currency = signUpRequestDto.currency
            this.language = signUpRequestDto.language
            this.theme = Theme.fromName(signUpRequestDto.theme) ?: Theme.System
            this.regId = savedUserInformation.userId
            this.regDt = LocalDateTime.now()
        }

        userConfigurationRepository.save(userConfiguration)

        validator.deviceId(signUpRequestDto.deviceInformationDto.deviceId)
        validator.deviceType(signUpRequestDto.deviceInformationDto.deviceType)
        validator.os(signUpRequestDto.deviceInformationDto.os)
        validator.platform(signUpRequestDto.deviceInformationDto.platform)

        val ip = getClientIp(request)

        validator.ip(ip)

        val hashedIp = crypto.hmacSha512(ip)

        val deviceInformation = DeviceInformation().apply {
            this.deviceId = signUpRequestDto.deviceInformationDto.deviceId
            this.deviceType = signUpRequestDto.deviceInformationDto.deviceType
            this.os = signUpRequestDto.deviceInformationDto.os
            this.platform = signUpRequestDto.deviceInformationDto.platform
            this.ip = hashedIp
            this.regDt = LocalDateTime.now()
        }

        deviceInformationRepository.save(deviceInformation)


        val accessToken = jwtProvider.generateAccessToken(
            userSeq = savedUserInformation.userSeq,
            userId = savedUserInformation.userId,
            name = savedUserInformation.name,
            email = savedUserInformation.email
        )

        val refreshToken = jwtProvider.generateRefreshToken()

        val refreshExpDt = LocalDateTime.ofInstant(
            Instant.now().plusMillis(jwtProperties.refreshTokenExpireTime),
            ZoneId.systemDefault()
        )

        val refreshTokenEntity = RefreshToken().apply {
            this.userInformation = savedUserInformation
            this.deviceInformation = deviceInformation
            this.refreshToken = refreshToken
            this.expDt = refreshExpDt
            this.regDt = LocalDateTime.now()
        }

        refreshTokenRepository.save(refreshTokenEntity)

        val response = SignUpResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userInformation = AuthUserInformationDto(
                userSeq = savedUserInformation.userSeq,
                userId =  savedUserInformation.userId,
                name = savedUserInformation.name,
                email = savedUserInformation.email,
            )
        )

        return ResponseEntity.ok(response)
    }

    fun checkDuplicateEmail(email: String): ResponseEntity<DuplicateResponse> {
        validator.email(email)

        val exists = userRepository.existsByEmail(email)

        val response = if (exists) {
            DuplicateResponse(
                isDuplicate = true,
                message = "Email already exists"
            )
        } else {
            DuplicateResponse(
                isDuplicate = false,
                message = "Email is available"
            )
        }

        return ResponseEntity.ok(response)
    }


    private fun getClientIp(request: HttpServletRequest): String {
        val headers = listOf(
            "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
        )

        for (header in headers) {
            val ip = request.getHeader(header)
            if (!ip.isNullOrEmpty() && !"unknown".equals(ip, ignoreCase = true)) {
                return ip.split(",")[0].trim()
            }
        }

        return request.remoteAddr
    }
}