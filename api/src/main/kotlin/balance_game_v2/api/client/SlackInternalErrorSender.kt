package balance_game_v2.api.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.slack.api.Slack
import com.slack.api.model.block.Blocks
import com.slack.api.model.block.Blocks.divider
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.webhook.WebhookPayloads
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper

@Component
class SlackInternalErrorSender(
    private val objectMapper: ObjectMapper,
    @Value("\${slack.token}")
    private val slackToken: String,
) {
    fun execute(cachingRequest: ContentCachingRequestWrapper, e: Exception, userId: Long?) {
        val url = cachingRequest.requestURL.toString()
        val method = cachingRequest.method
        val body = objectMapper.readTree(cachingRequest.contentAsByteArray).toString()
        val errorMessage = e.message
        val errorUserIp = cachingRequest.remoteAddr

        val layoutBlocks = mutableListOf<LayoutBlock>()

        layoutBlocks.add(
            Blocks.header {
                header ->
                header.text(
                    PlainTextObject.builder()
                        .text("Error Detection")
                        .build()
                )
            }
        )
        layoutBlocks.add(divider())

        val errorUserIdMarkdown = MarkdownTextObject.builder()
            .text("* User Id: *\n" + userId)
            .build()

        val errorUserIpMarkdown = MarkdownTextObject.builder()
            .text("* User Ip: *\n" + errorUserIp)
            .build()

        layoutBlocks.add(
            section {
                section ->
                section.fields(listOf(errorUserIdMarkdown, errorUserIpMarkdown))
            }
        )

        val methodMarkdown = MarkdownTextObject.builder()
            .text("* Request Addr: *\n" + method + "\n" + url)
            .build()

        val bodyMarkdown = MarkdownTextObject.builder()
            .text("* Request Body: *\n" + body)
            .build()

        layoutBlocks.add(
            section {
                section ->
                section.fields(listOf(methodMarkdown, bodyMarkdown))
            }
        )

        val errorMessageMarkdown = MarkdownTextObject.builder()
            .text("* Error Message *\n" + errorMessage)
            .build()

        layoutBlocks.add(
            section {
                section ->
                section.fields(listOf(errorMessageMarkdown))
            }
        )

        println(slackToken)
        println(layoutBlocks.toString())

        Slack.getInstance().send(
            slackToken,
            WebhookPayloads.payload { p ->
                p.blocks(
                    layoutBlocks
                )
            }
        )
    }
}
