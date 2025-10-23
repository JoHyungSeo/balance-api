package jooyung.com.joomoney_api.auth.repository

import jooyung.com.joomoney_api.auth.entity.EmailToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EmailTokenRepository: JpaRepository<EmailToken, Long>{
    @Query(
        value = """
            SELECT *
            FROM email_token
            WHERE email = :email
            ORDER BY reg_dt DESC
            LIMIT 1
        """,
        nativeQuery = true
    )
    fun findByEmail(
        @Param("email") email: String): EmailToken?
}