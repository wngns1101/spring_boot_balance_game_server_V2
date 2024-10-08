// package balance_game_v2.api.v2.board.http.req
//
// import balance_game_v2.domain.board.dto.ExcelBoardCommandDTO
//
// data class ExcelRequestDTO (
//    val introduce: String,
//    val title: String,
//    val userId: Long,
//    val themeId: Long,
//    val boardContents: List<String>,
//    val boardContentItems: List<String>,
//    val keywords: List<String>,
// )
//
// fun ExcelRequestDTO.toCommand(): ExcelBoardCommandDTO {
//    return ExcelBoardCommandDTO(
//        introduce = introduce,
//        title = title,
//        userId = userId,
//        themeId = themeId,
//        boardContents = boardContents,
//        boardContentItems = boardContentItems,
//        keywords = keywords,
//    )
// }
