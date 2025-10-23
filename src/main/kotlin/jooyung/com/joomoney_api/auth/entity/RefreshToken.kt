package jooyung.com.joomoney_api.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
open class RefreshToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_seq", nullable = false)
    var refreshTokenSeq: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false)
    var userInformation: UserInformation,

    @Column(name = "refresh_token", nullable = false, length = 255)
    var refreshToken: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_information_seq", nullable = false)
    var deviceInformation: DeviceInformation,

    @Column(name = "exp_dt", nullable = false)
    var expDt: LocalDateTime, // 토큰 만료 시각

    @Column(name = "reg_dt", nullable = false)
    var regDt: LocalDateTime = LocalDateTime.now()
)