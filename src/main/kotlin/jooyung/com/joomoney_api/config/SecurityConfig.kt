package jooyung.com.joomoney_api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll()
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // Argon2 parameters tuned for VARCHAR(255) storage
        val saltLength = 16        // 16 bytes = 22 base64 chars
        val hashLength = 64        // 64 bytes = ~86 base64 chars
        val parallelism = 2        // 2 threads (balanced performance)
        val memory = 1 shl 14      // 16MB memory (stronger than default 8MB)
        val iterations = 4         // repeat count (slightly slower but stronger)

        return Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations)
    }
}