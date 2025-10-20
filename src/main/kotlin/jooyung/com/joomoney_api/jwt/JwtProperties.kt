package jooyung.com.joomoney_api.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secretKey: String          // jwt.secret-key
    lateinit var tokenPrefix: String        // jwt.token-prefix
    lateinit var header: String             // jwt.header
    var accessTokenExpireTime: Long = 0     // jwt.access-token.expire-time
    var refreshTokenExpireTime: Long = 0    // jwt.refresh-token.expire-time
}
