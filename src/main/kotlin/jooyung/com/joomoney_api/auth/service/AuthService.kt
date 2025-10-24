package jooyung.com.joomoney_api.auth.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import jooyung.com.joomoney_api.auth.dto.AuthUserInformationDto
import jooyung.com.joomoney_api.auth.dto.request.EmailVerifyValidateRequest
import jooyung.com.joomoney_api.auth.dto.request.SignUpRequestDto
import jooyung.com.joomoney_api.auth.dto.response.DuplicateResponse
import jooyung.com.joomoney_api.auth.dto.response.EmailVerifyValidateResponse
import jooyung.com.joomoney_api.auth.dto.response.SignUpResponseDto
import jooyung.com.joomoney_api.auth.entity.*
import jooyung.com.joomoney_api.auth.repository.*
import jooyung.com.joomoney_api.enum.gender.Gender
import jooyung.com.joomoney_api.enum.theme.Theme
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
import java.util.UUID

@Service
class AuthService (
    private val crypto: Crypto,
    private val jwtProperties: JwtProperties,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val request: HttpServletRequest,
    private val deviceInformationRepository: DeviceInformationRepository,
    private val emailTokenRepository: EmailTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userConfigurationRepository: UserConfigurationRepository,
    private val userEmailRepository: UserEmailRepository,
    private val userInformationRepository: UserInformationRepository,
    private val validator: Validator,
) {

    fun emailVerifyRequest(email: String): ResponseEntity<Void> {
        validator.email(email)

        if (userInformationRepository.existsByEmail(email)) {
            throw ApiException(ResultCode.ERR_EMAIL_IS_DUPLICATE)
        }

        val existingEmailToken = emailTokenRepository.findByEmail(email)

        if (existingEmailToken != null) {
            if (existingEmailToken.isVerified == 'N' && existingEmailToken.codeExpiredDt.isAfter(LocalDateTime.now())) {
                throw ApiException(ResultCode.ERR_EMAIL_VERIFY_IN_PROGRESS)
            }
            if (
                existingEmailToken.isVerified == 'Y' &&
                existingEmailToken.verificationTokenExpiredDt?.isAfter(LocalDateTime.now()) == true &&
                existingEmailToken.verifiedTokenConsumedDt == null
            ) {
                throw ApiException(ResultCode.ERR_EMAIL_VERIFY_ALREADY_COMPLETED)
            }
        }

        val code = (100000..999999).random().toString()
        val codeExpiredDt = LocalDateTime.now().plusMinutes(5)

        val emailToken = EmailToken(
            email = email,
            code = code,
            codeExpiredDt = codeExpiredDt,
            isVerified = 'N',
            regDt = LocalDateTime.now()
        )

        emailTokenRepository.save(emailToken)

        return ResponseEntity.ok().build()
    }

    fun emailVerifyValidate(emailVerifyValidateRequest: EmailVerifyValidateRequest): ResponseEntity<EmailVerifyValidateResponse> {
        val (email, code) = emailVerifyValidateRequest

        val existingEmailToken = emailTokenRepository.findByEmail(email)

        when {
            existingEmailToken == null -> {
                throw ApiException(ResultCode.ERR_EMAIL_CODE_NOT_FOUND)
            }

            existingEmailToken.isVerified == 'Y' -> {
                throw ApiException(ResultCode.ERR_EMAIL_VERIFY_ALREADY_COMPLETED)
            }

            existingEmailToken.codeExpiredDt.isBefore(LocalDateTime.now()) -> {
                throw ApiException(ResultCode.ERR_EMAIL_CODE_EXPIRED)
            }

            // fail count 추가

            // fail count에 따른 15분 정지 추가

            existingEmailToken.code != code -> {
                throw ApiException(ResultCode.ERR_EMAIL_CODE_INVALID)
            }

            else -> {

            }
        }

        val randomUuid = UUID.randomUUID().toString()

        existingEmailToken.isVerified = 'Y'
        existingEmailToken.verificationToken = randomUuid
        existingEmailToken.verificationTokenExpiredDt = LocalDateTime.now().plusMinutes(15)

        emailTokenRepository.save(existingEmailToken)

        val response = EmailVerifyValidateResponse(
            verificationToken = randomUuid
        )

        return ResponseEntity.ok(response)
    }

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

        if (userInformationRepository.existsByUserId(signUpRequestDto.userId)) {
            throw ApiException(ResultCode.ERR_USER_ID_IS_DUPLICATE)
        }

        if (userInformationRepository.existsByEmail(signUpRequestDto.email)) {
            throw ApiException(ResultCode.ERR_EMAIL_IS_DUPLICATE)
        }

        val existingEmailToken = emailTokenRepository.findByEmail(email)

        if (existingEmailToken == null || existingEmailToken.isVerified != 'Y') {
            // 이메일 인증부터 하고 와라
        }

        if (existingEmailToken.verificationTokenExpiredDt.isBefore(LocalDateTime.now())) {
            throw ApiException(ResultCode.ERR_EMAIL_VERIFICATION_TOKEN_EXPIRED)
        }

        if (existingEmailToken.verificationToken != signUpRequestDto.verificationToken) {
            throw ApiException(ResultCode.ERR_EMAIL_VERIFICATION_TOKEN_INVALID)
        }

        existingEmailToken.verifiedTokenConsumedDt = LocalDateTime.now()
        emailTokenRepository.save(existingEmailToken)


        val encodedPassword = passwordEncoder.encode(signUpRequestDto.password)

        val userInformation = UserInformation(
            userId = signUpRequestDto.userId,
            name = signUpRequestDto.name,
            email = signUpRequestDto.email,
            password = encodedPassword,
            birthday = signUpRequestDto.birthday,
            gender = Gender.fromName(signUpRequestDto.gender),
            regId = signUpRequestDto.userId,
            regDt = LocalDateTime.now()
        )

        val savedUserInformation = userInformationRepository.save(userInformation)

        val userConfiguration = UserConfiguration(
            userSeq= savedUserInformation.userSeq,
            userInformation = savedUserInformation,
            payday = signUpRequestDto.payday,
            currency = signUpRequestDto.currency,
            language = signUpRequestDto.language,
            theme = Theme.fromName(signUpRequestDto.theme) ?: Theme.System,
            regId = savedUserInformation.userId,
            regDt = LocalDateTime.now(),
        )

        userConfigurationRepository.save(userConfiguration)

        validator.deviceId(signUpRequestDto.deviceInformationDto.deviceId)
        validator.deviceType(signUpRequestDto.deviceInformationDto.deviceType)
        validator.os(signUpRequestDto.deviceInformationDto.os)
        validator.platform(signUpRequestDto.deviceInformationDto.platform)

        val ip = getClientIp(request)

        validator.ip(ip)

        val hashedIp = crypto.hmacSha512(ip)

        val deviceInformation = DeviceInformation(
            deviceId = signUpRequestDto.deviceInformationDto.deviceId,
            deviceType = signUpRequestDto.deviceInformationDto.deviceType,
            os = signUpRequestDto.deviceInformationDto.os,
            platform = signUpRequestDto.deviceInformationDto.platform,
            ip = hashedIp,
            regDt = LocalDateTime.now()
        )

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

        val refreshTokenEntity = RefreshToken(
            userInformation = savedUserInformation,
            deviceInformation = deviceInformation,
            refreshToken = refreshToken,
            expDt = refreshExpDt,
            regDt = LocalDateTime.now(),
        )

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

    @Transactional
    fun checkDuplicateUserId(userId: String): ResponseEntity<DuplicateResponse> {
        validator.userId(userId)

        val exists = userInformationRepository.existsByUserId(userId)

        val response = if (exists) {
            DuplicateResponse(
                isDuplicate = true,
                message = "User id already exists"
            )
        } else {
            DuplicateResponse(
                isDuplicate = true,
                message = "User id is available"
            )
        }

        return ResponseEntity.ok(response)
    }

    @Transactional
    fun checkDuplicateEmail(email: String): ResponseEntity<DuplicateResponse> {
        validator.email(email)

        val exists = userInformationRepository.existsByEmail(email)

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