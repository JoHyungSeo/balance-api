package jooyung.com.joomoney_api.enum.theme

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = false)
class ThemeConverter : AttributeConverter<Theme, String> {

    override fun convertToDatabaseColumn(attribute: Theme?): String? {
        return attribute?.code?.toString() // Enum → 'S' / 'L' / 'D'
    }

    override fun convertToEntityAttribute(dbData: String?): Theme? {
        return dbData?.firstOrNull()?.let { Theme.fromCode(it) } // 'S' → Theme.System
    }
}