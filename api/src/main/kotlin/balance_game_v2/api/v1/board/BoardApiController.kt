package balance_game_v2.api.v1.board

import balance_game_v2.api.v1.board.application.BoardFacade
import balance_game_v2.api.v1.board.http.req.*
import balance_game_v2.api.v1.board.http.res.*
import balance_game_v2.api.v1.user.application.UserFacade
import balance_game_v2.config.BOARD_V2
import balance_game_v2.config.BOARD_V2_PREFIX
import domain.board.model.BoardSortCondition
import domain.board.repository.BoardResultRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = BOARD_V2)
@RestController
@RequestMapping(BOARD_V2_PREFIX)
class BoardApiController(
    val userFacade: UserFacade,
    val boardFacade: BoardFacade,
    private val boardResultRepository: BoardResultRepository,
) {
    @Operation(summary = "[게임-001] 게임 등록")
    @PostMapping("/board")
    fun createBoard(
        @RequestAttribute("email") email: String,
        @RequestBody request: CreateBoardRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        boardFacade.createBoard(user.userId, request)
    }

    @Operation(summary = "[게임-002] 게임 리스트 조회(페이징)")
    @GetMapping("/boards")
    fun getBoards(
        @RequestAttribute("email") email: String,
        @RequestParam("query") query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sortCondition") sortCondition: BoardSortCondition?,
    ): PageBoardResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return PageBoardResponseDTO(boardFacade.getBoards(query, page, size, sortCondition))
    }

    @Operation(summary = "[게임-003] 게임 상세 조회")
    @GetMapping("/boards/{boardId}")
    fun getBoardDetail(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardDetailResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return BoardDetailResponseDTO(boardFacade.getBoardDetail(boardId))
    }

    @Operation(summary = "[게임-004] 게임 좋아요")
    @PostMapping("/boards/{boardId}/heart")
    fun createBoardHeart(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardHeartResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return BoardHeartResponseDTO(boardFacade.boardHeart(boardId, user.userId))
    }

    @Operation(summary = "[게임-005] 게시글 게임 전체 조회")
    @GetMapping("/boards/{boardId}/contents")
    fun getBoardContents(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardContentResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return BoardContentResponseDTO(boardFacade.getBoardContents(boardId))
    }

    @Operation(summary = "[게임-006] 게시글 게임 결과 등록")
    @PostMapping("/boards/{boardId}/content")
    fun createBoardResult(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
        @RequestParam("boardContentId") boardContentId: Long,
    ) {
        val user = userFacade.getUserByEmail(email)

        return boardFacade.createBoardResult(boardId, boardContentId, user.userId)
    }

    @Operation(summary = "[게임-007] 게시글 게임 결과 조회")
    @GetMapping("/boards/{boardId}/results")
    fun getBoardResult(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardResultResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return BoardResultResponseDTO(boardFacade.getBoardResult(boardId))
    }

    @Operation(summary = "[게임-008] 게시글 게임 수정")
    @PutMapping("/boards/{boardId}")
    fun modifyBoard(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: BoardModifyRequestDTO
    ) {
        val user = userFacade.getUserByEmail(email)

        boardFacade.modifyBoard(boardId, request)
    }

    @Operation(summary = "[게임-009] 게시글 댓글 작성")
    @PostMapping("/boards/{boardId}/comment")
    fun createBoardComment(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: CreateBoardCommentRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        boardFacade.createBoardComment(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-010] 게시글 댓글 수정")
    @PutMapping("/boards/{boardId}/comment")
    fun modifyBoardComment(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: ModifyBoardCommentRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        boardFacade.modifyBoardComment(boardId, user.userId, request)
    }

    @Operation(summary = "[게임-011] 게시글 게임 삭제")
    @DeleteMapping("/boards/{boardId}")
    fun deleteBoardComment(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: DeleteBoardCommentRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        boardFacade.deleteBoardComment(boardId, user.userId, request)
    }
}