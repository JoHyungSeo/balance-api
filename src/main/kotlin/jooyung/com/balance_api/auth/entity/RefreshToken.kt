package jooyung.com.balance_api.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
open class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_seq")
    var refreshTokenSeq: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false)
    var userInformation: UserInformation? = null

    @Column(name = "refresh_token", length = 255, nullable = false)
    var refreshToken: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_information_seq", nullable = false)
    var deviceInformation: DeviceInformation? = null

    @Column(name = "exp_dt", nullable = false)
    var expDt: LocalDateTime = LocalDateTime.now()

    @Column(name = "reg_dt", nullable = false)
    var regDt: LocalDateTime = LocalDateTime.now()
}
