package jooyung.com.balance_api.util.crypto

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("crypto")
class CryptoProperties {
    lateinit var hashIpPepper: String
}