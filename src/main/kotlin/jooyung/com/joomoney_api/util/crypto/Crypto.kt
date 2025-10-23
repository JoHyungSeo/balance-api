package jooyung.com.joomoney_api.util.crypto

import org.springframework.stereotype.Component
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class Crypto(
    private val cryptoProperties: CryptoProperties
) {
    private val HMAC_ALGO = "HmacSHA512"

    fun hmacSha512(ip: String, salt: String? = null): String {
        val secretPepper = cryptoProperties.hashIpPepper

        val mac = Mac.getInstance(HMAC_ALGO)
        val keySpec = SecretKeySpec(secretPepper.toByteArray(Charsets.UTF_8), HMAC_ALGO)
        mac.init(keySpec)

        val normalized = normalizeIp(ip)
        val payload = if (salt.isNullOrBlank()) normalized else "$normalized|$salt"

        val digest = mac.doFinal(payload.toByteArray(Charsets.UTF_8))
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }

    private fun normalizeIp(ip: String): String =
        ip.trim().lowercase()
}