package jooyung.com.joomoney_api.enum.gender

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = false) // autoApply=false → Gender 필드에만 직접 지정
class GenderConverter : AttributeConverter<Gender, String> {

    override fun convertToDatabaseColumn(attribute: Gender?): String? {
        return attribute?.code?.toString() // Enum → 'M' / 'F' / 'O'
    }

    override fun convertToEntityAttribute(dbData: String?): Gender? {
        return dbData?.firstOrNull()?.let { Gender.fromCode(it) } // 'M' → Gender.MALE
    }
}