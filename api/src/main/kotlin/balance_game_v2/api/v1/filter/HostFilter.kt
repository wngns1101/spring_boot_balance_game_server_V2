package balance_game_v2.api.v1.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value

class HostFilter : Filter {
    @Value("\${dns}")
    private val dns: String? = null
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val req: HttpServletRequest = request as HttpServletRequest
        val res: HttpServletResponse = response as HttpServletResponse

        val host = req.getHeader("Host")
        println(host)
        if (!host.equals("localhost") && !host.equals(dns)) {
            response.getWriter().write("Forbidden")
            response.getWriter().flush()
            return
        }

        chain.doFilter(request, response)
    }
}
