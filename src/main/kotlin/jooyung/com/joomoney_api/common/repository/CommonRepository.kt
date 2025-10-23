package jooyung.com.joomoney_api.common.repository

import jooyung.com.joomoney_api.common.entity.CommonCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommonRepository : JpaRepository<CommonCode, Long> {
    fun findByCommonCodeGroup_GroupCd(groupCd: String): List<CommonCode>

    fun existsByCommonCodeGroup_GroupCdAndCode(groupCd: String, code: String): Boolean
}