package balance_game_v2.api.v1.board

import balance_game_v2.api.v1.board.application.BoardFacade
import balance_game_v2.api.v1.board.http.req.BoardModifyRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardCommentRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v1.board.http.req.DeleteBoardCommentRequestDTO
import balance_game_v2.api.v1.board.http.req.ModifyBoardCommentRequestDTO
import balance_game_v2.api.v1.board.http.res.BoardContentResponseDTO
import balance_game_v2.api.v1.board.http.res.BoardDetailResponseDTO
import balance_game_v2.api.v1.board.http.res.BoardHeartResponseDTO
import balance_game_v2.api.v1.board.http.res.BoardResultResponseDTO
import balance_game_v2.api.v1.board.http.res.PageBoardResponseDTO
import balance_game_v2.api.v1.board.http.res.TodayRecommendGameResponseDTO
import balance_game_v2.api.v1.user.application.UserFacade
import balance_game_v2.config.BOARD_V2
import balance_game_v2.config.BOARD_V2_PREFIX
import domain.board.model.BoardSortCondition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = BOARD_V2)
@RestController
@RequestMapping(BOARD_V2_PREFIX)
class BoardApiController(
    val userFacade: UserFacade,
    val boardFacade: BoardFacade,
) {
    @Operation(summary = "[게임-001] 게임 등록")
    @PostMapping("/board")
    fun createBoard(
        @RequestAttribute("accountName") accountName: String,
        @RequestBody request: CreateBoardRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.createBoard(user.userId, request)
    }

    @Operation(summary = "[게임-002] 게임 리스트 조회(페이징)")
    @GetMapping("/boards")
    fun getBoards(
//        @RequestAttribute("accountName") accountName: String,
        @RequestParam("query") query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sortCondition") sortCondition: BoardSortCondition?,
        @RequestParam("themeId") themeId: Long,
    ): PageBoardResponseDTO {
//        val user = userFacade.getUserByEmail(accountName)

        return PageBoardResponseDTO(boardFacade.getBoards(query, page, size, sortCondition, themeId))
    }

    @Operation(summary = "[게임-003] 게임 상세 조회")
    @GetMapping("/boards/{boardId}")
    fun getBoardDetail(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardDetailResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return BoardDetailResponseDTO(boardFacade.getBoardDetail(boardId))
    }

    @Operation(summary = "[게임-004] 게임 좋아요")
    @PostMapping("/boards/{boardId}/heart")
    fun createBoardHeart(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardHeartResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return BoardHeartResponseDTO(boardFacade.boardHeart(boardId, user.userId))
    }

    @Operation(summary = "[게임-005] 게시글 게임 전체 조회")
    @GetMapping("/boards/{boardId}/contents")
    fun getBoardContents(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardContentResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return BoardContentResponseDTO(boardFacade.getBoardContents(boardId))
    }

    @Operation(summary = "[게임-006] 게시글 게임 결과 등록")
    @PostMapping("/boards/{boardId}/content")
    fun createBoardResult(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestParam("boardContentId") boardContentId: Long,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        return boardFacade.createBoardResult(boardId, boardContentId, user.userId)
    }

    @Operation(summary = "[게임-007] 게시글 게임 결과 조회")
    @GetMapping("/boards/{boardId}/results")
    fun getBoardResult(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardResultResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return BoardResultResponseDTO(boardFacade.getBoardResult(boardId))
    }

    @Operation(summary = "[게임-008] 게시글 게임 수정")
    @PutMapping("/boards/{boardId}")
    fun modifyBoard(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: BoardModifyRequestDTO
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.modifyBoard(boardId, request)
    }

    @Operation(summary = "[게임-009] 게시글 댓글 작성")
    @PostMapping("/comments/{boardId}/comment")
    fun createBoardComment(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: CreateBoardCommentRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.createBoardComment(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-010] 게시글 댓글 수정")
    @PutMapping("/comments/{boardId}/comment")
    fun modifyBoardComment(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: ModifyBoardCommentRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.modifyBoardComment(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-011] 게시글 게임 삭제")
    @DeleteMapping("/boards/{boardId}")
    fun deleteBoardComment(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: DeleteBoardCommentRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.deleteBoardComment(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-012] 게시글 신고")
    @PostMapping("/boards/{boardId}/report")
    fun createBoardReport(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.createBoardReport(boardId, user.userId)
    }

    @Operation(summary = "[게임-013] 댓글 신고")
    @PostMapping("/comments/{boardCommentId}/report")
    fun createBoardCommentReport(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardCommentId") boardCommentId: Long,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.createBoardCommentReport(boardCommentId, user.userId)
    }

    @Operation(summary = "[게임-014] 오늘의 추천 밸런스 게임 조회")
    @GetMapping("/boards/today-recommend-game")
    fun todayRecommendGame(): TodayRecommendGameResponseDTO {
        return TodayRecommendGameResponseDTO(boardFacade.todayRecommendGame())
    }
}
