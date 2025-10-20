package jooyung.com.balance_api.common.dto.response

data class CommonCodeItemDto(
    val code: String,
    val codeName: String
)

data class CommonCodeResponseDto(
    val groupCd: String,
    val groupNm: String,
    val codes: List<CommonCodeItemDto>
)