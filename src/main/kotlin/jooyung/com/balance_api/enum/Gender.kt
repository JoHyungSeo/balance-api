package jooyung.com.balance_api.enum

enum class Gender(val code: Char) {
    MALE('M'),
    FEMALE('F'),
    OTHER('O');

    companion object {
        fun fromCode(code: Char?): Gender? {
            return entries.find { it.code == code }
        }

        fun fromName(name: String?): Gender? {
            return entries.find { it.name.equals(name, ignoreCase = true) }
        }
    }
}