package jooyung.com.balance_api.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "common_code")
open class CommonCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    var codeId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_cd", nullable = false)
    var group: CommonCodeGroup? = null

    @Column(name = "code", length = 100, nullable = false)
    var code: String = ""

    @Column(name = "code_nm", length = 100, nullable = false)
    var codeNm: String = ""

    @Column(name = "order_no", nullable = false)
    var orderNo: Int = 0

    @Column(name = "use_yn", length = 1, nullable = false)
    var useYn: Char = 'Y' // Y or N

    @Column(name = "reg_id", length = 50, nullable = false)
    var regId: String = ""

    @Column(name = "reg_dt", nullable = false)
    var regDt: LocalDateTime = LocalDateTime.now()

    @Column(name = "mod_id", length = 50)
    var modId: String? = null

    @Column(name = "mod_dt")
    var modDt: LocalDateTime? = null
}
