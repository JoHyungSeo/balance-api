package jooyung.com.joomoney_api.common.controller

import jooyung.com.joomoney_api.common.dto.response.CommonCodeResponseDto
import jooyung.com.joomoney_api.common.service.CommonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/common")
class CommonController(
    private val commonService: CommonService
) {

    @GetMapping("/code/{groupCd}")
    fun getCommonCodes(@PathVariable groupCd: String): ResponseEntity<CommonCodeResponseDto> {
        return commonService.getCommonCodes(groupCd)
    }

}
