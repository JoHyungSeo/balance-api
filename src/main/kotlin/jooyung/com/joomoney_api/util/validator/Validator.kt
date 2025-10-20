package jooyung.com.joomoney_api.util.validator

import jooyung.com.joomoney_api.JoomoneyConstants
import jooyung.com.joomoney_api.common.repository.CommonRepository
import jooyung.com.joomoney_api.enum.Gender
import jooyung.com.joomoney_api.enum.Theme
import jooyung.com.joomoney_api.exception.ApiException
import jooyung.com.joomoney_api.exception.ResultCode
import org.springframework.stereotype.Component
import java.time.LocalDate
import kotlin.text.isBlank

@Component
class Validator(
    private val commonRepository: CommonRepository
) {

    fun userId(userId: String) {
        if (userId.isBlank()) throw ApiException(ResultCode.ERR_USER_ID_IS_EMPTY)
        if (userId.length > 50) throw ApiException(ResultCode.ERR_USER_ID_IS_OVER_50_CHAR)
    }

    fun name(name: String) {
        if (name.isBlank()) throw ApiException(ResultCode.ERR_NAME_IS_EMPTY)
        if (name.length > 50) throw ApiException(ResultCode.ERR_NAME_IS_OVER_50_CHAR)
    }

    fun email(email: String) {
        if (email.isBlank()) throw ApiException(ResultCode.ERR_EMAIL_IS_EMPTY)

        if (email.length > 255) throw ApiException(ResultCode.ERR_EMAIL_IS_OVER_255_CHAR)

        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if (!regex.matches(email)) {
            throw ApiException(ResultCode.ERR_EMAIL_IS_INVALID)
        }
    }

    fun password(password: String) {
        if (password.isBlank()) throw ApiException(ResultCode.ERR_PASSWORD_IS_EMPTY)

        if (password.length !in 8..128)
            throw ApiException(ResultCode.ERR_PASSWORD_IS_OVER_128_CHAR_AND_UNDER_8_CHAR)

        if (!password.any { it.isUpperCase() })
            throw ApiException(ResultCode.ERR_PASSWORD_NEEDS_UPPERCASE)

        if (!password.any { it.isDigit() })
            throw ApiException(ResultCode.ERR_PASSWORD_NEEDS_NUMBER)

        if (!Regex("[!@#\$%^&*(),.?\":{}|<>_\\-]").containsMatchIn(password))
            throw ApiException(ResultCode.ERR_PASSWORD_NEEDS_SPECIAL_CHAR)
    }

    fun birthday(birthday: LocalDate?) {
        if (birthday == null) return

        val today = LocalDate.now()
        if (birthday.isAfter(today)) {
            throw ApiException(ResultCode.ERR_BIRTHDAY_IS_FUTURE)
        }
    }

    fun gender(gender: String?) {
        if (gender.isNullOrBlank()) return

        val matchedGender = Gender.fromName(gender)
            ?: Gender.fromCode(gender.firstOrNull())

        if (matchedGender == null) {
            throw ApiException(ResultCode.ERR_GENDER_IS_INVALID)
        }
    }

    fun payday(payday: String?) {
        if (payday.isNullOrBlank()) return

        val day = payday.toIntOrNull()
            ?: throw ApiException(ResultCode.ERR_PAYDAY_IS_NOT_NUMBER)

        if (day !in 1..31) {
            throw ApiException(ResultCode.ERR_PAYDAY_IS_OUT_OF_RANGE)
        }
    }

    fun currency(currency: String) {
        if (currency.isBlank()) throw ApiException(ResultCode.ERR_CURRENCY_IS_EMPTY)

        val exists = commonRepository.existsByGroup_GroupCdAndCode(JoomoneyConstants.GROUP_CURRENCY, currency)

        if (!exists) {
            throw ApiException(ResultCode.ERR_CURRENCY_IS_INVALID)
        }
    }

    fun language(language: String) {
        if (language.isBlank()) throw ApiException(ResultCode.ERR_LANGUAGE_IS_EMPTY)

        val exists = commonRepository.existsByGroup_GroupCdAndCode(JoomoneyConstants.GROUP_LANGUAGE, language)

        if (!exists) {
            throw ApiException(ResultCode.ERR_LANGUAGE_IS_INVALID)
        }
    }

    fun theme(theme: String) {
        if (theme.isBlank()) throw ApiException(ResultCode.ERR_THEME_IS_EMPTY)

        val matchedTheme = Theme.fromName(theme)
            ?: Theme.fromCode(theme.firstOrNull())

        if (matchedTheme == null) {
            throw ApiException(ResultCode.ERR_THEME_IS_INVALID)
        }
    }

    fun deviceId(deviceId: String) {
        if (deviceId.isBlank()) throw ApiException(ResultCode.ERR_DEVICE_ID_IS_EMPTY)
    }

    fun deviceType(deviceType: String) {
        if (deviceType.isBlank()) throw ApiException(ResultCode.ERR_DEVICE_TYPE_IS_EMPTY)

        val exists = commonRepository.existsByGroup_GroupCdAndCode(JoomoneyConstants.GROUP_DEVICE, deviceType)

        if (!exists) {
            throw ApiException(ResultCode.ERR_DEVICE_TYPE_IS_INVALID)
        }
    }

    fun os(os: String) {
        if (os.isBlank()) throw ApiException(ResultCode.ERR_OS_IS_EMPTY)

        val exists = commonRepository.existsByGroup_GroupCdAndCode(JoomoneyConstants.GROUP_OS, os)

        if (!exists) {
            throw ApiException(ResultCode.ERR_OS_IS_INVALID)
        }
    }

    fun platform(platform: String) {
        if (platform.isBlank()) throw ApiException(ResultCode.ERR_PLATFORM_IS_EMPTY)

        val exists = commonRepository.existsByGroup_GroupCdAndCode(JoomoneyConstants.GROUP_PLATFORM, platform)

        if (!exists) {
            throw ApiException(ResultCode.ERR_PLATFORM_IS_INVALID)
        }
    }

    fun ip(ip: String) {

    }
}