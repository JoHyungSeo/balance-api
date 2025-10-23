package jooyung.com.joomoney_api.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "email_token")
open class EmailToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_token_seq", nullable = false)
    val emailTokenSeq: Long = 0L,

    @Column(name = "email", nullable = false, length = 255)
    val email: String,

    @Column(name = "code", nullable = false, columnDefinition = "char(6)")
    val code: String,

    @Column(name = "code_expired_dt", nullable = false)
    val codeExpiredDt: LocalDateTime,

    @Column(name = "is_verified", nullable = false, columnDefinition = "char(1)")
    var isVerified: Char = 'N',

    @Column(name = "verification_token", length = 36)
    var verificationToken: String? = null,

    @Column(name = "verification_token_expired_dt")
    var verificationTokenExpiredDt: LocalDateTime? = null,

    @Column(name = "verified_token_consumed_dt")
    var verifiedTokenConsumedDt: LocalDateTime? = null,

    @Column(name = "reg_dt", nullable = false)
    val regDt: LocalDateTime = LocalDateTime.now()
)