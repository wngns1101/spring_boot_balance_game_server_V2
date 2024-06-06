package balance_game_v2.api.v1.user.application

import domain.auth.exception.InvalidTokenException
import domain.auth.model.AuthGroup
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
        return claims.get("email").toString()
    }

    fun getAuthGroupFromToken(token: String): AuthGroup {
        val claims = getClaimsFromToken(token)
        return claims.get("authGroup") as AuthGroup
    }

    fun isTokenExpired(token: String): Boolean {
        val date = Date()
        val claims = getClaimsFromToken(token)
        return if (claims.expiration.before(date)) false else true
    }

    fun validationAccessToken(accessToken: String) {
        val claims = getClaimsFromToken(accessToken)

        if (claims["tokenType"] != "accessToken") {
            throw InvalidTokenException()
        }
    }

    fun validationRefreshToken(refreshToken: String) {
        val claims = getClaimsFromToken(refreshToken)

        if (claims["tokenType"] != "refreshToken") {
            throw InvalidTokenException()
        }
    }

    fun makeJwtToken(email: String, authGroup: AuthGroup): TokenDTO {
        val accessToken = createAccessToken(email, authGroup)
        val refreshToken = createRefreshToken(email, authGroup)
        return TokenDTO(accessToken, refreshToken)
    }

    fun refreshToken(refreshToken: String): TokenDTO {
        validationRefreshToken(refreshToken)

        val email = getUserEmailFromToken(refreshToken)
        val authGroup = getAuthGroupFromToken(refreshToken)

        return makeJwtToken(email, authGroup)
    }
}
