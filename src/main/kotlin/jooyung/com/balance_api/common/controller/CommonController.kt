package jooyung.com.balance_api.common.controller

import jooyung.com.balance_api.common.dto.CommonCodeResponseDto
import jooyung.com.balance_api.common.service.CommonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
