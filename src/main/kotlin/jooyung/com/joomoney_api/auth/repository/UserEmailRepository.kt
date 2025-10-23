package jooyung.com.joomoney_api.auth.repository

import jooyung.com.joomoney_api.auth.entity.UserEmail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEmailRepository: JpaRepository<UserEmail, Long> {
    fun existsByEmailAndUseYn(email: String, useYn: Char): Boolean
}