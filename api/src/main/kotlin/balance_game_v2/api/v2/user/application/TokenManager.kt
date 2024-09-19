package balance_game_v2.api.v2.user.application

import balance_game_v2.domain.auth.model.AuthGroup
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.Date

@Configuration
class TokenManager(
    @Value("\${secret-key}")
    val baseKey: String,
    @Value("\${access-token-expire}")
    val accessTokenExpire: Long,
    @Value("\${refresh-token-expire}")
    val refreshTokenExpire: Long,
) {
    val secret = SignatureAlgorithm.HS256

    fun createAccessToken(email: String, authGroup: AuthGroup): String {
        val header = HashMap<String, Any>()
        header["type"] = "JWT"

        val claims = HashMap<String, Any>()
        claims["email"] = email
        claims["authGroup"] = authGroup
        claims["tokenType"] = "accessToken"

        val date = Date()
        date.time = date.time + (accessTokenExpire)

        return Jwts.builder()
            .setHeader(header)
            .setClaims(claims)
            .setExpiration(date)
            .signWith(secret, baseKey.toByteArray())
            .compact()
    }

    fun createRefreshToken(email: String, authGroup: AuthGroup): String {
        val header = HashMap<String, Any>()
        header["type"] = "JWT"

        val claims = HashMap<String, Any>()
        claims["email"] = email
        claims["authGroup"] = authGroup
        claims["tokenType"] = "refreshToken"

        val date = Date()
        date.time = date.time + (refreshTokenExpire)

        return Jwts.builder()
            .setHeader(header)
            .setClaims(claims)
            .setExpiration(date)
            .signWith(secret, baseKey.toByteArray())
            .compact()
    }

    fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(baseKey.toByteArray())
            .parseClaimsJws(token).body
    }

    fun getUserEmailFromToken(token: String): String {
        val claims = getClaimsFromToken(token)
        return claims["email"].toString()
    }

    fun getAuthGroupFromToken(token: String): AuthGroup {
        val claims = getClaimsFromToken(token)
        return AuthGroup.valueOf(claims["authGroup"] as String)
    }

    fun isTokenExpired(token: String): Boolean {
        val date = Date()
        val claims = getClaimsFromToken(token)
        return claims.expiration.before(date)
    }

    fun validationAccessToken(accessToken: String): Boolean {
        val claims = getClaimsFromToken(accessToken)

        return claims["tokenType"] == "accessToken"
    }

    fun validationRefreshToken(refreshToken: String): Boolean {
        val claims = getClaimsFromToken(refreshToken)

        return claims["tokenType"] == "refreshToken"
    }

    fun makeJwtToken(email: String, authGroup: AuthGroup): TokenDTO {
        val accessToken = createAccessToken(email, authGroup)
        val refreshToken = createRefreshToken(email, authGroup)
        return TokenDTO(accessToken, refreshToken)
    }

    fun refreshToken(refreshToken: String): TokenDTO {
        val email = getUserEmailFromToken(refreshToken)
        val authGroup = getAuthGroupFromToken(refreshToken)

        return makeJwtToken(email, authGroup)
    }
}
