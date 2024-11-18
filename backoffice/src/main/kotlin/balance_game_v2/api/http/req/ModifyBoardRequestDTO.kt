package balance_game_v2.api.http.req

import balance_game_v2.domain.board.dto.BoardCommandDTO
import balance_game_v2.domain.board.dto.BoardContentCommandDTO
import balance_game_v2.domain.board.dto.BoardContentItemCommandDTO
import balance_game_v2.domain.board.dto.ModifyBoardCommandDTO

data class ModifyBoardRequestDTO(
    val board: BoardRequestDTO,
    val boardContents: List<BoardContentRequestDTO>
)

data class BoardRequestDTO(
    val boardId: Long,
    val themeId: Long,
    val title: String,
    val introduce: String,
    val keywords: String?,
)

data class BoardContentRequestDTO(
    val boardContentId: Long,
    val title: String?,
    val boardContentItems: List<BoardContentItemRequestDTO>
)

data class BoardContentItemRequestDTO(
    val boardContentItemId: Long,
    val item: String,
)

fun ModifyBoardRequestDTO.toCommand(): ModifyBoardCommandDTO {
    return ModifyBoardCommandDTO(
        board = board.toCommand(),
        boardContents = boardContents.map { it.toCommand() }
    )
}

fun BoardRequestDTO.toCommand(): BoardCommandDTO {
    return BoardCommandDTO(
        boardId = boardId,
        themeId = themeId,
        title = title,
        introduce = introduce,
        keywords = keywords
    )
}

fun BoardContentRequestDTO.toCommand(): BoardContentCommandDTO {
    return BoardContentCommandDTO(
        boardContentId = boardContentId,
        title = title,
        boardContentItems = boardContentItems.map { it.toCommand() }
    )
}

fun BoardContentItemRequestDTO.toCommand(): BoardContentItemCommandDTO {
    return BoardContentItemCommandDTO(
        boardContentItemId = boardContentItemId,
        item = item
    )
}
