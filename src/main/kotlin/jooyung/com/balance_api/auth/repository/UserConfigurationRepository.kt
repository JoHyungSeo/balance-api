package jooyung.com.balance_api.auth.repository

import jooyung.com.balance_api.auth.entity.UserConfiguration
import org.springframework.data.jpa.repository.JpaRepository

interface UserConfigurationRepository: JpaRepository<UserConfiguration, Long> {

}