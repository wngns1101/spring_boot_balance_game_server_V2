package balance_game_v2.api.v1.board.http.req

import domain.domain.board.dto.CreateBoardResultRequestCommand

data class CreateBoardResultRequestDTO(
    val boardContentId: Long,
    val boardContentItemId: Long,
)

fun CreateBoardResultRequestDTO.toCommand(): CreateBoardResultRequestCommand {
    return CreateBoardResultRequestCommand(
        boardContentId = boardContentId,
        boardContentItemId = boardContentItemId,
    )
}
