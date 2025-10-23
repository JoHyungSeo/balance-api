package jooyung.com.joomoney_api.common.service

import jakarta.transaction.Transactional
import jooyung.com.joomoney_api.common.dto.response.CommonCodeItemDto
import jooyung.com.joomoney_api.common.dto.response.CommonCodeResponseDto
import jooyung.com.joomoney_api.common.repository.CommonRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommonService (
    private val commonRepository: CommonRepository
) {
    @Transactional
    fun getCommonCodes(groupCd: String): ResponseEntity<CommonCodeResponseDto> {
        val codeList = commonRepository.findByCommonCodeGroup_GroupCd(groupCd)

        if (codeList.isEmpty()) {
            return ResponseEntity.notFound().build()
        }

        val groupNm = codeList.first().commonCodeGroup.groupNm

        val codeItems = codeList
            .filter { it.useYn == 'Y' }
            .sortedBy { it.orderNo }
            .map {
                CommonCodeItemDto(
                    code = it.code,
                    codeName = it.codeNm
                )
            }

        val response = CommonCodeResponseDto(
            groupCd = groupCd,
            groupNm = groupNm,
            codes = codeItems
        )

        return ResponseEntity.ok(response)
    }
}