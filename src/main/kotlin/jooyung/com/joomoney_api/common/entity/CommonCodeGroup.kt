package jooyung.com.joomoney_api.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "common_code_group")
data class CommonCodeGroup (
    @Id
    @Column(name = "group_cd", nullable = false, length = 50)
    val groupCd: String,

    @Column(name = "group_nm", nullable = false, length = 50)
    val groupNm: String,

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    val useYn: Char = 'Y', // 'Y' or 'N'

    @Column(name = "reg_id", nullable = false, length = 50)
    val regId: String,

    @Column(name = "reg_dt", nullable = false)
    val regDt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "mod_id", length = 50)
    val modId: String? = null,

    @Column(name = "mod_dt")
    val modDt: LocalDateTime? = null,

    @OneToMany(mappedBy = "commonCodeGroup", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val codes: List<CommonCode> = emptyList()
)