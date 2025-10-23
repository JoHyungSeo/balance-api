package jooyung.com.joomoney_api.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "common_code")
data class CommonCode (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id", nullable = false)
    val codeId: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_cd", nullable = false)
    val commonCodeGroup: CommonCodeGroup,

    @Column(name = "code", nullable = false, length = 100)
    val code: String,

    @Column(name = "code_nm", nullable = false, length = 100)
    val codeNm: String,

    @Column(name = "order_no", nullable = false)
    val orderNo: Int,

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    val useYn: Char = 'Y', // 'Y' or 'N'

    @Column(name = "reg_id", nullable = false, length = 50)
    val regId: String,

    @Column(name = "reg_dt", nullable = false)
    val regDt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "mod_id", length = 50)
    val modId: String? = null,

    @Column(name = "mod_dt")
    val modDt: LocalDateTime? = null
)