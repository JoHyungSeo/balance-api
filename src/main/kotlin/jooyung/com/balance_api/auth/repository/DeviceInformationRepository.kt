package jooyung.com.balance_api.auth.repository

import jooyung.com.balance_api.auth.entity.DeviceInformation
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceInformationRepository: JpaRepository<DeviceInformation, Long> {

}