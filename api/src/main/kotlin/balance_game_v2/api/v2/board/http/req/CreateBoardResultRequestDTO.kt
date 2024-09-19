package balance_game_v2.api.v2.board.http.req

import balance_game_v2.domain.board.dto.CreateBoardResultRequestCommand

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
