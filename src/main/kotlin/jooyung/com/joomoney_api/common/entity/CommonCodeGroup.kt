package jooyung.com.joomoney_api.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "common_code_group")
open class CommonCodeGroup {
    @Id
    @Column(name = "group_cd", length = 50)
    var groupCd: String = ""

    @Column(name = "group_nm", length = 50, nullable = false)
    var groupNm: String = ""

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

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var codes: MutableList<CommonCode> = mutableListOf()
}
