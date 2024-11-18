package balance_game_v2.domain.board.dto

data class ModifyBoardCommandDTO(
    val board: BoardCommandDTO,
    val boardContents: List<BoardContentCommandDTO>
)

data class BoardCommandDTO(
    val boardId: Long,
    val themeId: Long,
    val title: String,
    val introduce: String,
    val keywords: String?,
)

data class BoardContentCommandDTO(
    val boardContentId: Long,
    val title: String?,
    val boardContentItems: List<BoardContentItemCommandDTO>
)

data class BoardContentItemCommandDTO(
    val boardContentItemId: Long,
    val item: String,
)
