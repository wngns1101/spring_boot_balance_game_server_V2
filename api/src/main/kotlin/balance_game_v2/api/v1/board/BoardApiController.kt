package balance_game_v2.api.v1.board

import balance_game_v2.api.v1.board.application.BoardFacade
import balance_game_v2.api.v1.board.http.req.BoardModifyRequestDTO
import balance_game_v2.api.v1.board.http.req.BoardReportRequestDTO
import balance_game_v2.api.v1.board.http.req.BoardReviewReportRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardResultRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardReviewRequestDTO
import balance_game_v2.api.v1.board.http.req.DeleteBoardReviewRequestDTO
import balance_game_v2.api.v1.board.http.req.ModifyBoardReviewRequestDTO
import balance_game_v2.api.v1.board.http.res.BoardContentResponseDTO
import balance_game_v2.api.v1.board.http.res.BoardDetailResponseDTO
import balance_game_v2.api.v1.board.http.res.BoardResultResponseDTO
import balance_game_v2.api.v1.board.http.res.GetMyBoardsResponseDTO
import balance_game_v2.api.v1.board.http.res.GetParticipatedGamesResponseDTO
import balance_game_v2.api.v1.board.http.res.GetReviewsResponseDTO
import balance_game_v2.api.v1.board.http.res.GetWroteReviewsResponseDTO
import balance_game_v2.api.v1.board.http.res.PageBoardResponseDTO
import balance_game_v2.api.v1.board.http.res.RelatedBoardsResponseDTO
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
        @RequestParam("query") query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sortCondition") sortCondition: BoardSortCondition?,
        @RequestParam("themeId") themeId: Long?,
        @RequestAttribute("accountName") accountName: String?,
    ): PageBoardResponseDTO {
        val userId = if (accountName != null) {
            userFacade.getUserByAccountName(accountName).userId
        } else {
            null
        }

        return PageBoardResponseDTO(boardFacade.getBoards(query, page, size, sortCondition, themeId, userId))
    }

    @Operation(summary = "[게임-003] 게임 상세 조회")
    @GetMapping("/public/boards/{boardId}")
    fun getBoardDetail(
        @PathVariable("boardId") boardId: Long,
    ): BoardDetailResponseDTO {
        return BoardDetailResponseDTO(boardFacade.getBoardDetail(boardId))
    }

//    @Operation(summary = "[게임-004] 게임 평가(좋아요, 싫어요)")
//    @PostMapping("/boards/{boardId}/evaluation")
//    fun boardEvaluation(
//        @RequestAttribute("accountName") accountName: String,
//        @PathVariable("boardId") boardId: Long,
//        @RequestBody request: EvaluationBoardRequest,
//    ): BoardEvaluationResponseDTO {
//        val user = userFacade.getUserByAccountName(accountName)
//
//        return BoardEvaluationResponseDTO(boardFacade.boardEvaluation(boardId, user.userId, request))
//    }

    @Operation(summary = "[게임-005] 게시글 게임 전체 조회")
    @GetMapping("/boards/{boardId}/contents")
    fun getBoardContents(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardContentResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return BoardContentResponseDTO(boardFacade.getBoardContents(boardId, user.userId))
    }

    @Operation(summary = "[게임-006] 게시글 게임 결과 등록")
    @PostMapping("/boards/{boardId}/result")
    fun createBoardResult(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: List<CreateBoardResultRequestDTO>,
    ): BoardResultResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return BoardResultResponseDTO(boardFacade.createBoardResult(boardId, request, user.userId))
    }

//    @Operation(summary = "[게임-007] 게시글 게임 결과 조회")
//    @GetMapping("/boards/{boardId}/results")
//    fun getBoardResult(
//        @RequestAttribute("accountName") accountName: String,
//        @PathVariable("boardId") boardId: Long,
//    ): BoardResultResponseDTO {
//        val user = userFacade.getUserByAccountName(accountName)
//
//        return BoardResultResponseDTO(boardFacade.getBoardResult(boardId))
//    }

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

    @Operation(summary = "[게임-009] 게시글 리뷰 작성")
    @PostMapping("/boards/{boardId}/review")
    fun createBoardComment(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: CreateBoardReviewRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)
        boardFacade.createBoardReview(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-010] 게시글 댓글 수정")
    @PutMapping("/boards/{boardId}/review")
    fun modifyBoardReview(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: ModifyBoardReviewRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.modifyBoardReview(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-011] 게시글 리뷰 삭제")
    @DeleteMapping("/boards/{boardId}/review")
    fun deleteBoardReview(
        @RequestAttribute("accountName") accountName: String,
        @RequestBody request: DeleteBoardReviewRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.deleteBoardReview(user.userId, request)
    }

    @Operation(summary = "[게임-012] 게시글 신고")
    @PostMapping("/boards/{boardId}/report")
    fun createBoardReport(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: BoardReportRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.createBoardReport(boardId, user.userId, request.content)
    }

    @Operation(summary = "[게임-013] 리뷰 신고")
    @PostMapping("/boards/{boardReviewId}/report")
    fun createBoardReviewReport(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("boardReviewId") boardReviewId: Long,
        @RequestBody request: BoardReviewReportRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        boardFacade.createBoardReviewReport(boardReviewId, user.userId, request.content)
    }

    @Operation(summary = "[게임-014] 오늘의 추천 밸런스 게임 조회")
    @GetMapping("/boards/today-recommend-game")
    fun todayRecommendGame(): TodayRecommendGameResponseDTO {
        return TodayRecommendGameResponseDTO(boardFacade.todayRecommendGame())
    }

    @Operation(summary = "[게임-015] 내 게임 조회")
    @GetMapping("/boards/me")
    fun getMyBoards(
        @RequestAttribute("accountName") accountName: String,
    ): GetMyBoardsResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return GetMyBoardsResponseDTO(boardFacade.getMyBoards(user.userId))
    }

    @Operation(summary = "[게임-016] 관련있는 게임 API")
    @GetMapping("/boards/{boardId}/related-boards")
    fun relatedBoards(
        @PathVariable("boardId") boardId: Long,
        @RequestAttribute("accountName") accountName: String?,
    ): RelatedBoardsResponseDTO {
        val userId = if (accountName != null) {
            userFacade.getUserByAccountName(accountName).userId
        } else {
            null
        }

        return RelatedBoardsResponseDTO(boardFacade.relatedBoards(boardId, userId))
    }

    @Operation(summary = "[게임-017] 리뷰 조회 API")
    @GetMapping("/public/boards/{boardId}/reviews")
    fun getReviews(
        @PathVariable("boardId") boardId: Long,
    ): GetReviewsResponseDTO {
        return GetReviewsResponseDTO(boardFacade.getReviews(boardId))
    }

    @Operation(summary = "[게임-018] 내가 참여한 게임 조회")
    @GetMapping("/boards/me/participated-games")
    fun getParticipatedGames(
        @RequestAttribute("accountName") accountName: String,
    ): GetParticipatedGamesResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)
        return GetParticipatedGamesResponseDTO(boardFacade.getParticipatedGames(user.userId))
    }

    @Operation(summary = "[게임-019] 내가 작성한 리뷰 리스트")
    @GetMapping("/boards/me/wrote-reviews")
    fun getWroteReviews(
        @RequestAttribute("accountName") accountName: String,
    ): GetWroteReviewsResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)
        return GetWroteReviewsResponseDTO(boardFacade.getWroteReviews(user.userId))
    }
}
