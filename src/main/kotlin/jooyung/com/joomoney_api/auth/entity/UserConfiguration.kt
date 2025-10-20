package jooyung.com.joomoney_api.auth.entity

import jakarta.persistence.*
import jooyung.com.joomoney_api.enum.Theme
import java.time.LocalDateTime

@Entity
@Table(name = "user_configuration")
open class UserConfiguration {
    @Id
    @Column(name = "user_seq")
    var userSeq: Long? = null

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_seq")
    var userInformation: UserInformation? = null

    @Column(name = "payday", length = 2)
    var payday: String? = null

    @Column(name = "currency", length = 3, nullable = false)
    var currency: String = ""

    @Column(name = "language", length = 5, nullable = false)
    var language: String = ""

    @Enumerated(EnumType.STRING)
    @Column(name = "theme", length = 1, nullable = false)
    var theme: Theme = Theme.System // S = System, L = Light, D = Dark

    @Column(name = "reg_id", length = 50, nullable = false)
    var regId: String = ""

    @Column(name = "reg_dt", nullable = false)
    var regDt: LocalDateTime = LocalDateTime.now()

    @Column(name = "mod_id", length = 50)
    var modId: String? = null

    @Column(name = "mod_dt")
    var modDt: LocalDateTime? = null
}
