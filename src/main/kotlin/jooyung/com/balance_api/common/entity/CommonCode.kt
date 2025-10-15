package jooyung.com.balance_api.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "common_code")
data class CommonCode(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    val codeId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_cd", referencedColumnName = "group_cd")
    val group: CommonCodeGroup,

    @Column(name = "code", length = 100)
    val code: String? = null,

    @Column(name = "code_nm", length = 100)
    val codeNm: String? = null,

    @Column(name = "order_no")
    val orderNo: Int? = null,

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
