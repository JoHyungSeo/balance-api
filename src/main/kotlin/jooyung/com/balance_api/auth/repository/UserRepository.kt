package jooyung.com.balance_api.auth.repository

import jooyung.com.balance_api.auth.entity.UserInformation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserInformation, Long> {
    fun existsByUserId(userId: String): Boolean

    fun existsByEmail(email: String): Boolean
}