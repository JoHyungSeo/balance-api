package jooyung.com.balance_api.auth.entity

import jakarta.persistence.*
import jooyung.com.balance_api.enum.Gender
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "user_information",
    uniqueConstraints = [
        UniqueConstraint(name = "user_id_unique", columnNames = ["user_id"]),
        UniqueConstraint(name = "user_email_unique", columnNames = ["email"])
    ]
)
open class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    var userSeq: Long = 0

    @Column(name = "user_id", length = 50, nullable = false)
    var userId: String = ""

    @Column(name = "name", length = 50, nullable = false)
    var name: String = ""

    @Column(name = "email", length = 255, nullable = false)
    var email: String = ""

    @Column(name = "password", length = 255, nullable = false)
    var password: String = ""

    @Column(name = "birthday")
    var birthday: LocalDate? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 1)
    var gender: Gender? = null // 'M' = Male, 'F' = Female, 'O' = Other

    @Column(name = "reg_id", length = 100, nullable = false)
    var regId: String = ""

    @Column(name = "reg_dt", nullable = false)
    var regDt: LocalDateTime = LocalDateTime.now()

    @Column(name = "mod_id", length = 100)
    var modId: String? = null

    @Column(name = "mod_dt")
    var modDt: LocalDateTime? = null
}
