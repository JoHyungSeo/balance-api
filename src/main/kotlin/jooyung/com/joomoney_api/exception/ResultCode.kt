package jooyung.com.joomoney_api.exception

enum class ResultCode(
    val code: String,
    val status: String,
    val message: String
) {
    SUCCESS("SUCCESS", "200", "Success"),
    ERR_BAD_REQUEST("ERR_BAD_REQUEST", "400", "Bad request"),
    ERR_SYSTEM("ERR_SYSTEM", "500", "Internal server error"),

    // USER
    ERR_USER_ID_IS_EMPTY("ERR_USER_ID_IS_EMPTY", "400.01", "User ID is required"),
    ERR_USER_ID_IS_OVER_50_CHAR("ERR_USER_ID_IS_OVER_50_CHAR", "400.02", "User ID cannot exceed 50 characters"),
    ERR_USER_ID_IS_DUPLICATE("ERR_USER_ID_IS_DUPLICATE", "400.03", "User ID already exists"),

    // NAME
    ERR_NAME_IS_EMPTY("ERR_NAME_IS_EMPTY", "400.04", "Name is required"),
    ERR_NAME_IS_OVER_50_CHAR("ERR_NAME_IS_OVER_50_CHAR", "400.05", "Name cannot exceed 50 characters"),

    // EMAIL
    ERR_EMAIL_IS_EMPTY("ERR_EMAIL_IS_EMPTY", "400.06", "Email is required"),
    ERR_EMAIL_IS_OVER_255_CHAR("ERR_EMAIL_IS_OVER_255_CHAR", "400.07", "Email cannot exceed 255 characters"),
    ERR_EMAIL_IS_INVALID("ERR_EMAIL_IS_INVALID", "400.08", "Invalid email format"),
    ERR_EMAIL_IS_DUPLICATE("ERR_EMAIL_IS_DUPLICATE", "400.09", "Email already exists"),

    // EMAIL VERIFICATION
    ERR_EMAIL_VERIFY_IN_PROGRESS("ERR_EMAIL_VERIFY_IN_PROGRESS", "400.34", " Verification code already sent"),
    ERR_EMAIL_VERIFY_ALREADY_COMPLETED("ERR_EMAIL_VERIFY_ALREADY_COMPLETED", "400.35", "Email verification already completed"),

    // EMAIL CODE
    ERR_EMAIL_CODE_NOT_FOUND("ERR_EMAIL_CODE_NOT_FOUND", "400.36", "Verification code not found"),
    ERR_EMAIL_CODE_EXPIRED("ERR_EMAIL_CODE_EXPIRED", "400.37", "Verification code has expired"),
    ERR_EMAIL_CODE_INVALID("ERR_EMAIL_CODE_INVALID", "400.38", "Invalid verification code"),

    // PASSWORD
    ERR_PASSWORD_IS_EMPTY("ERR_PASSWORD_IS_EMPTY", "400.10", "Password is required"),
    ERR_PASSWORD_IS_OVER_128_CHAR_AND_UNDER_8_CHAR("ERR_PASSWORD_IS_OVER_128_CHAR_AND_UNDER_8_CHAR", "400.11", "Password must be between 8 and 128 characters"),
    ERR_PASSWORD_NEEDS_UPPERCASE("ERR_PASSWORD_NEEDS_UPPERCASE", "400.12", "Password must contain at least one uppercase letter"),
    ERR_PASSWORD_NEEDS_NUMBER("ERR_PASSWORD_NEEDS_NUMBER", "400.13", "Password must contain at least one number"),
    ERR_PASSWORD_NEEDS_SPECIAL_CHAR("ERR_PASSWORD_NEEDS_SPECIAL_CHAR", "400.14", "Password must contain at least one special character"),

    // BIRTHDAY
    ERR_BIRTHDAY_IS_FUTURE("ERR_BIRTHDAY_IS_FUTURE", "400.15", "Birthday cannot be in the future"),

    // GENDER
    ERR_GENDER_IS_INVALID("ERR_GENDER_IS_INVALID", "400.16", "Invalid gender"),

    // PAYDAY
    ERR_PAYDAY_IS_NOT_NUMBER("ERR_PAYDAY_IS_NOT_NUMBER", "400.17", "Payday must be a number between 1 and 31"),
    ERR_PAYDAY_IS_OUT_OF_RANGE("ERR_PAYDAY_IS_OUT_OF_RANGE", "400.18", "Payday must be between 1 and 31"),

    // CURRENCY
    ERR_CURRENCY_IS_EMPTY("ERR_CURRENCY_IS_EMPTY", "400.19", "Currency is required"),
    ERR_CURRENCY_IS_INVALID("ERR_CURRENCY_IS_INVALID", "400.20", "Invalid currency"),

    // LANGUAGE
    ERR_LANGUAGE_IS_EMPTY("ERR_LANGUAGE_IS_EMPTY", "400.21", "Language is required"),
    ERR_LANGUAGE_IS_INVALID("ERR_LANGUAGE_IS_INVALID", "400.22", "Invalid language"),

    // THEME
    ERR_THEME_IS_EMPTY("ERR_THEME_IS_EMPTY", "400.23", "Theme is required"),
    ERR_THEME_IS_INVALID("ERR_THEME_IS_INVALID", "400.24", "Invalid theme"),

    // DEVICE
    ERR_DEVICE_ID_IS_EMPTY("ERR_DEVICE_ID_IS_EMPTY", "400.25", "Device ID is required"),
    ERR_DEVICE_TYPE_IS_EMPTY("ERR_DEVICE_TYPE_IS_EMPTY", "400.26", "Device type is required"),
    ERR_DEVICE_TYPE_IS_INVALID("ERR_DEVICE_TYPE_IS_INVALID", "400.27", "Invalid device type"),
    ERR_OS_IS_EMPTY("ERR_OS_IS_EMPTY", "400.28", "OS is required"),
    ERR_OS_IS_INVALID("ERR_OS_IS_INVALID", "400.29", "Invalid OS"),
    ERR_PLATFORM_IS_EMPTY("ERR_PLATFORM_IS_EMPTY", "400.30", "Platform is required"),
    ERR_PLATFORM_IS_INVALID("ERR_PLATFORM_IS_INVALID", "400.31", "Invalid platform"),

    // IP
    ERR_IP_IS_EMPTY("ERR_IP_IS_EMPTY", "400.32", "IP address is required"),
    ERR_IP_IS_INVALID("ERR_IP_IS_INVALID", "400.33", "Invalid IP address"),
}
