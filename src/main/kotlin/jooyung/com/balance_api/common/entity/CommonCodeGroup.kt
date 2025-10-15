package jooyung.com.balance_api.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "common_code_group")
data class CommonCodeGroup(
    @Id
    @Column(name = "group_cd", length = 50)
    val groupCd: String,

    @Column(name = "group_nm", length = 50)
    val groupNm: String? = null,

    @Column(name = "use_yn", length = 1)
    val useYn: String? = null,

    @Column(name = "reg_id", length = 50)
    val regId: String? = null,

    @Column(name = "reg_dt")
    val regDt: LocalDateTime? = null,

    @Column(name = "mod_id", length = 50)
    val modId: String? = null,

    @Column(name = "mod_dt")
    val modDt: LocalDateTime? = null
)
