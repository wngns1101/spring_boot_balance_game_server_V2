package domain.board

import domain.auth.exception.NotFoundUserException
import domain.board.dto.BoardContentDTO
import domain.board.dto.BoardDetailDTO
import domain.board.dto.BoardResultDTO
import domain.board.dto.CreateBoardCommand
import domain.board.dto.CreateBoardReviewCommand
import domain.board.dto.DeleteBoardReviewCommand
import domain.board.dto.ModifyBoardCommand
import domain.board.dto.ModifyBoardReviewCommand
import domain.board.dto.PageBoardDTO
import domain.board.dto.WriterDTO
import domain.board.dto.toBoardDetail
import domain.board.dto.toDTO
import domain.board.dto.toPageBoardDTO
import domain.board.entity.Board
import domain.board.entity.BoardCommentReport
import domain.board.entity.BoardContent
import domain.board.entity.BoardContentItem
import domain.board.entity.BoardEvaluationHistory
import domain.board.entity.BoardHeart
import domain.board.entity.BoardKeyword
import domain.board.entity.BoardReport
import domain.board.entity.BoardResult
import domain.board.entity.BoardReview
import domain.board.entity.BoardReviewKeyword
import domain.board.exception.NotFoundException
import domain.board.model.BoardSortCondition
import domain.board.repository.BoardCommentReportRepository
import domain.board.repository.BoardContentItemRepository
import domain.board.repository.BoardContentRepository
import domain.board.repository.BoardEvaluationHistoryRepository
import domain.board.repository.BoardHeartRepository
import domain.board.repository.BoardKeywordRepository
import domain.board.repository.BoardReportRepository
import domain.board.repository.BoardRepository
import domain.board.repository.BoardResultRepository
import domain.board.repository.BoardReviewKeywordRepository
import domain.board.repository.BoardReviewRepository
import domain.domain.board.dto.CreateBoardResultRequestCommand
import domain.domain.board.dto.SimpleBoardDTO
import domain.domain.board.dto.toDTO
import domain.domain.board.dto.toSimpleBoard
import domain.error.InvalidUserException
import domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val boardReviewKeywordRepository: BoardReviewKeywordRepository,
    private val boardHeartRepository: BoardHeartRepository,
    private val boardResultRepository: BoardResultRepository,
    private val boardReviewRepository: BoardReviewRepository,
    private val boardReportRepository: BoardReportRepository,
    private val boardCommentReportRepository: BoardCommentReportRepository,
    private val boardContentItemRepository: BoardContentItemRepository,
    private val boardKeywordRepository: BoardKeywordRepository,
    private val boardContentRepository: BoardContentRepository,
    private val boardEvaluationHistoryRepository: BoardEvaluationHistoryRepository,
) {
    @Transactional
    fun createBoard(userId: Long, command: CreateBoardCommand) {
        val savedBoard = Board(
            themeId = command.themeId,
            userId = userId,
            title = command.title,
            introduce = command.introduce,
        ).let {
            boardRepository.save(it)
        }

        command.keywords.map {
            BoardKeyword(
                boardId = savedBoard.boardId!!,
                keyword = it,
            )
        }.let {
            boardKeywordRepository.saveAll(it)
        }

        command.boardContents.map { it ->
            val savedBoardContent = BoardContent(
                boardId = savedBoard.boardId!!,
                title = it.title,
            ).let {
                boardContentRepository.save(it)
            }

            it.items.map {
                BoardContentItem(
                    boardContentId = savedBoardContent.boardContentId!!,
                    item = it,
                )
            }.let {
                boardContentItemRepository.saveAll(it)
            }
        }
    }

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?, themeId: Long): PageBoardDTO {
        val pageable = PageRequest.of(page, size)
        val boards = boardRepository.search(query, pageable, sortCondition, themeId)
        val boardIds = boards.content.mapNotNull { it.boardId }

        val boardKeywords = boardKeywordRepository.findAllByBoardIdIn(boardIds)
        val boardKeywordsMap = boardKeywords.groupBy { it.boardId }
            .mapValues { it.value.map { it.toDTO() } }

        return PageBoardDTO(
            boards = boards.content.map {
                it.toPageBoardDTO(
                    boardKeywordsMap[it.boardId!!] ?: emptyList()
                )
            },
            totalPage = boards.totalPages,
        )
    }

    @Transactional
    fun getBoardDetail(boardId: Long): BoardDetailDTO {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        board.viewCount += 1

        val writer = userRepository.findById(board.userId).orElseThrow { NotFoundUserException() }

        val boardReviews = boardReviewRepository.findAllByBoardId(boardId)

        val previewBoardReviewKeywords = if (boardReviews == null) {
            emptyList()
        } else {
            boardReviewKeywordRepository.findAllByBoardReviewIdIn(boardReviews.map { it.boardReviewId!! })
                .shuffled()
                .take(3)
                .map { it.toDTO() }
        }

        return board.toBoardDetail(
            WriterDTO(
                writer.userId!!,
                writer.nickname,
            ),
            previewBoardReviewKeywords
        )
    }

    @Transactional
    fun boardHeart(boardId: Long, userId: Long): Boolean {
        val board = boardRepository.findById(boardId).orElseThrow { NotFoundException() }
        val exist = boardHeartRepository.existsByBoardIdAndUserId(boardId, userId)

        if (exist) {
            val boardHeart = boardHeartRepository.findByBoardIdAndUserId(boardId, userId)
            boardHeartRepository.delete(boardHeart)

            board.likeCount -= 1
            return false
        } else {
            BoardHeart(
                boardId = boardId,
                userId = userId,
            ).let { boardHeartRepository.save(it) }

            board.likeCount += 1
            return true
        }
    }

    fun getBoardContents(boardId: Long): List<BoardContentDTO> {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        val boardContents = boardContentRepository.findAllByBoardId(boardId)
        val boardContentIds = boardContents.mapNotNull { it.boardContentId }

        val boardContentItems = boardContentItemRepository.findAllByBoardContentIdIn(boardContentIds)

        val boardContentResults = boardResultRepository.findAllByBoardContentItemIdIn(boardContentItems.mapNotNull { it.boardContentItemId })
            .groupingBy {
                it.boardContentItemId
            }.eachCount()

        val boardContentMap = boardContentItems.map {
            it.toDTO(boardContentResults[it.boardContentItemId] ?: 0)
        }.groupBy { it.boardContentId }

        return boardContents.map {
            it.toDTO(
                boardContentMap[it.boardContentId]!!
            )
        }
    }

    @Transactional
    fun createBoardResult(boardId: Long, command: List<CreateBoardResultRequestCommand>, userId: Long) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()

        boardResultRepository.findAllByBoardIdAndUserId(boardId, userId)
            .let { boardResultRepository.deleteAll(it) }

        command.map {
            BoardResult(
                boardContentItemId = it.boardContentItemId,
                boardContentId = it.boardContentId,
                boardId = board.boardId!!,
                userId = userId,
            )
        }.let { boardResultRepository.saveAll(it) }
    }

    fun getBoardResult(boardId: Long): List<BoardResultDTO> {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        val boardContents = boardContentRepository.findAllByBoardId(boardId)
        val boardContentIds = boardContents.mapNotNull { it.boardContentId }

        val boardContentItems = boardContentItemRepository.findAllByBoardContentIdIn(boardContentIds)

        val boardContentResults = boardResultRepository.findAllByBoardContentItemIdIn(boardContentItems.mapNotNull { it.boardContentItemId })
            .groupingBy {
                it.boardContentItemId
            }.eachCount()

        val boardContentMap = boardContentItems.map {
            it.toDTO(boardContentResults[it.boardContentItemId] ?: 0)
        }.groupBy { it.boardContentId }

        return boardContents.map {
            BoardResultDTO(
                boardContentId = it.boardContentId!!,
                title = it.title,
                boardContentItems = boardContentMap[it.boardContentId]!!,
            )
        }
    }

    @Transactional
    fun modifyBoard(boardId: Long, command: ModifyBoardCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        board.title = command.title
        board.introduce = command.content

        val boardContents = boardContentRepository.findAllByBoardId(board.boardId!!)
        boardContentRepository.deleteAll(boardContents)

        command.boardContent.map {
            BoardContent(
                boardId = board.boardId,
                title = it.title
            )
        }.let { boardContentRepository.saveAll(it) }
    }

    @Transactional
    fun createBoardReview(boardId: Long, userId: Long, command: CreateBoardReviewCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()

        BoardEvaluationHistory(
            boardId = board.boardId!!,
            userId = userId,
            isLike = command.isLike,
            isDislike = command.isDislike,
        ).let { boardEvaluationHistoryRepository.save(it) }

        val boardReview = BoardReview(
            boardId = board.boardId,
            userId = userId,
            comment = command.comment
        ).let { boardReviewRepository.save(it) }

        command.keywords.map {
            BoardReviewKeyword(
                boardReviewId = boardReview.boardReviewId!!,
                userId = userId,
                keyword = it,
            )
        }.let { boardReviewKeywordRepository.saveAll(it) }
    }

    @Transactional
    fun modifyBoardReview(boardId: Long, userId: Long, command: ModifyBoardReviewCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        val boardReview = boardReviewRepository.findById(command.boardReviewId).orElseThrow { NotFoundException() }

        if (boardReview.userId != userId) throw InvalidUserException()
        boardReview.comment = command.comment

        boardReviewKeywordRepository.findAllByBoardReviewId(boardReview.boardReviewId!!)
            .let {
                boardReviewKeywordRepository.deleteAll(it)
            }

        command.keywords.map {
            BoardReviewKeyword(
                boardReviewId = boardReview.boardReviewId,
                userId = userId,
                keyword = it,
            )
        }.let {
            boardReviewKeywordRepository.saveAll(it)
        }
    }

    @Transactional
    fun deleteBoardReview(userId: Long, command: DeleteBoardReviewCommand) {
        val boardReview = boardReviewRepository.findById(command.boardReviewId).orElseThrow { NotFoundException() }

        if (boardReview.userId != userId) throw InvalidUserException()

        boardReviewRepository.delete(boardReview)

        boardReviewKeywordRepository.findAllByBoardReviewId(boardReview.boardReviewId!!)
            .let { boardReviewKeywordRepository.deleteAll(it) }
    }

    @Transactional
    fun createBoardReport(boardId: Long, userId: Long) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()

        BoardReport(
            boardId = board.boardId!!,
            userId = userId,
        ).let { boardReportRepository.save(it) }
    }

    @Transactional
    fun createBoardCommentReport(boardCommentId: Long, userId: Long) {
        val boardComment = boardReviewRepository.findById(boardCommentId).orElseThrow { NotFoundException() }

        BoardCommentReport(
            boardCommentId = boardComment.boardId,
            userId = userId,
        ).let { boardCommentReportRepository.save(it) }
    }

//    fun getBoardReports(userId: Long): List<BoardReportDTO> {
//        return boardReportRepository.findAllByUserId(userId).map { it.toDTO() }
//    }
//
//    fun getBoardCommentReports(userId: Long): List<BoardCommentReportDTO> {
//        return boardCommentReportRepository.findAllByUserId(userId).map { it.toDTO() }
//    }

    fun todayRecommendGame(): SimpleBoardDTO {
        return boardRepository.todayRecommendGame().random().toSimpleBoard()
    }

    fun getMyBoardCount(userId: Long): Int {
        return boardRepository.countBoardByUserId(userId)
    }

    fun getMyBoards(userId: Long): List<SimpleBoardDTO> {
        return boardRepository.findAllByUserId(userId).map {
            it.toSimpleBoard()
        }
    }

    fun relatedBoards(boardId: Long): List<SimpleBoardDTO> {
        val board = boardRepository.findById(boardId).orElseThrow { NotFoundException() }

        return boardRepository.relatedBoards(boardId, board.themeId).map {
            it.toSimpleBoard()
        }
    }
}
