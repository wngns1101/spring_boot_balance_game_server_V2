package domain.domain.board.dto

data class CreateBoardResultRequestCommand(
    val boardContentId: Long,
    val boardContentItemId: Long,
)
