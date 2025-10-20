package jooyung.com.balance_api.auth.repository

import jooyung.com.balance_api.auth.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
}