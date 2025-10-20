package jooyung.com.joomoney_api.enum

enum class Theme(val code: Char) {
    System('S'),
    Light('L'),
    Dark('D');

    companion object {
        fun fromCode(code: Char?): Theme? {
            return entries.find { it.code == code }
        }

        fun fromName(name: String?): Theme? {
            return entries.find { it.name.equals(name, ignoreCase = true) }
        }
    }
}