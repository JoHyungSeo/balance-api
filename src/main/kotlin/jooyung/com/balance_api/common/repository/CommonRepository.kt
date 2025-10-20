package jooyung.com.balance_api.common.repository

import jooyung.com.balance_api.common.entity.CommonCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommonRepository : JpaRepository<CommonCode, Long> {
    fun findByGroup_GroupCd(groupCd: String): List<CommonCode>

    fun existsByGroup_GroupCdAndCode(groupCd: String, code: String): Boolean
}