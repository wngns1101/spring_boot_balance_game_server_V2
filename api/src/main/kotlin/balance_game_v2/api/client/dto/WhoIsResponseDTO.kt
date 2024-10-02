package balance_game_v2.api.client.dto

data class WhoIsResponseDTO(
    val response: Response
)

data class Response(
    val result: Result,
    val whois: Whois
)

data class Result(
    val result_code: String,
    val result_msg: String
)

data class Whois(
    val query: String,
    val queryType: String,
    val registry: String,
    val countryCode: String,
)
