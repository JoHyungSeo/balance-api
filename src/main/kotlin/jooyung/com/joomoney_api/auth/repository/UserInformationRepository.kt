package jooyung.com.joomoney_api.auth.repository

import jooyung.com.joomoney_api.auth.entity.UserInformation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInformationRepository : JpaRepository<UserInformation, Long> {
    fun existsByUserId(userId: String): Boolean

    fun existsByEmail(email: String): Boolean
}