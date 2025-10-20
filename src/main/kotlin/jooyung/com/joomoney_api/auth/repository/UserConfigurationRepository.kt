package jooyung.com.joomoney_api.auth.repository

import jooyung.com.joomoney_api.auth.entity.UserConfiguration
import org.springframework.data.jpa.repository.JpaRepository

interface UserConfigurationRepository: JpaRepository<UserConfiguration, Long> {

}