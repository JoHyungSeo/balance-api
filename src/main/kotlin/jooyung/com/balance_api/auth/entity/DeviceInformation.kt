package jooyung.com.balance_api.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "device_information")
open class DeviceInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_seq")
    var deviceSeq: Long? = null

    @Column(name = "device_id", length = 32, nullable = false)
    var deviceId: String = "" // UUIDv4 (32자)

    @Column(name = "device_type", length = 10, nullable = false)
    var deviceType: String = "" // PC, Mobile, Tablet

    @Column(name = "os", length = 10, nullable = false)
    var os: String = "" // Windows, macOS, Android, iOS

    @Column(name = "platform", length = 5, nullable = false)
    var platform: String = "" // Web, App

    @Column(name = "ip", length = 128, nullable = false)
    var ip: String = "" // 암호화된 IP - HMAC-SHA512

    @Column(name = "reg_dt", nullable = false)
    var regDt: LocalDateTime = LocalDateTime.now()
}
