package jooyung.com.joomoney_api.auth.repository

import jooyung.com.joomoney_api.auth.entity.DeviceInformation
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceInformationRepository: JpaRepository<DeviceInformation, Long> {

}