package balance_game_v2.domain.board

import balance_game_v2.domain.auth.exception.NotFoundUserException
import balance_game_v2.domain.board.dto.BoardContentList
import balance_game_v2.domain.board.dto.BoardDetailDTO
import balance_game_v2.domain.board.dto.BoardListDTO
import balance_game_v2.domain.board.dto.BoardResultDTO
import balance_game_v2.domain.board.dto.BoardReviewDTO
import balance_game_v2.domain.board.dto.CreateBoardCommand
import balance_game_v2.domain.board.dto.CreateBoardResultRequestCommand
import balance_game_v2.domain.board.dto.CreateBoardReviewCommand
import balance_game_v2.domain.board.dto.ModifyBoardCommand
import balance_game_v2.domain.board.dto.ModifyBoardReviewCommand
import balance_game_v2.domain.board.dto.PageBoardDTO
import balance_game_v2.domain.board.dto.ParticipatedBoardDTO
import balance_game_v2.domain.board.dto.SimpleBoardDTO
import balance_game_v2.domain.board.dto.WriterDTO
import balance_game_v2.domain.board.dto.toBoardDetail
import balance_game_v2.domain.board.dto.toDTO
import balance_game_v2.domain.board.dto.toPageBoardDTO
import balance_game_v2.domain.board.dto.toParticipatedBoardDTO
import balance_game_v2.domain.board.dto.toSimpleBoard
import balance_game_v2.domain.board.entity.Board
import balance_game_v2.domain.board.entity.BoardContent
import balance_game_v2.domain.board.entity.BoardContentItem
import balance_game_v2.domain.board.entity.BoardKeyword
import balance_game_v2.domain.board.entity.BoardReport
import balance_game_v2.domain.board.entity.BoardResult
import balance_game_v2.domain.board.entity.BoardReview
import balance_game_v2.domain.board.entity.BoardReviewKeyword
import balance_game_v2.domain.board.entity.BoardReviewReport
import balance_game_v2.domain.board.model.BoardSortCondition
import balance_game_v2.domain.board.repository.BoardContentItemRepository
import balance_game_v2.domain.board.repository.BoardContentRepository
import balance_game_v2.domain.board.repository.BoardKeywordRepository
import balance_game_v2.domain.board.repository.BoardReportRepository
import balance_game_v2.domain.board.repository.BoardRepository
import balance_game_v2.domain.board.repository.BoardResultRepository
import balance_game_v2.domain.board.repository.BoardReviewKeywordRepository
import balance_game_v2.domain.board.repository.BoardReviewReportRepository
import balance_game_v2.domain.board.repository.BoardReviewRepository
import balance_game_v2.domain.error.InvalidUserException
import balance_game_v2.domain.error.NotFoundBoardException
import balance_game_v2.domain.error.NotFoundReviewException
import balance_game_v2.domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val boardReviewKeywordRepository: BoardReviewKeywordRepository,
    private val boardResultRepository: BoardResultRepository,
    private val boardReviewRepository: BoardReviewRepository,
    private val boardReportRepository: BoardReportRepository,
    private val boardReviewReportRepository: BoardReviewReportRepository,
    private val boardContentItemRepository: BoardContentItemRepository,
    private val boardKeywordRepository: BoardKeywordRepository,
    private val boardContentRepository: BoardContentRepository,
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

    fun getBoards(
        query: String?,
        page: Int,
        size: Int,
        sortCondition: BoardSortCondition?,
        themeId: Long?,
        userId: Long?,
    ): PageBoardDTO {
        val pageable = PageRequest.of(page, size)

        val boardReportIds = if (userId != null) {
            boardReportRepository.findAllByUserId(userId).map { it.boardId }
        } else {
            null
        }

        val boards = boardRepository.search(query, pageable, sortCondition, themeId, boardReportIds)
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
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()
        board.viewCount += 1

        val writer = userRepository.findById(board.userId).orElseThrow { NotFoundUserException() }

        val boardReviews = boardReviewRepository.findAllByBoardId(boardId)

        val boardReviewCount = boardReviews?.size ?: 0

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
            boardReviewCount,
            previewBoardReviewKeywords
        )
    }
//    @Transactional
//    fun boardEvaluation(boardId: Long, userId: Long, command: EvaluationBoardCommand): BoardEvaluationDTO {
//        val board = boardRepository.findById(boardId).orElseThrow { NotFoundException() }
//
//        if (command.isLike) {
//            val boardLikeEvaluation = boardEvaluationHistoryRepository.findByBoardIdAndUserIdAndIsLikeTrue(board.boardId!!, userId)
//            val isLike = if (boardLikeEvaluation == null) {
//                boardEvaluationHistoryRepository.save(
//                    BoardEvaluationHistory(
//                        boardId = board.boardId,
//                        userId = userId,
//                        isLike = true,
//                        isDislike = false,
//                    )
//                )
//                board.likeCount += 1
//                true
//            } else {
//                boardEvaluationHistoryRepository.delete(boardLikeEvaluation)
//                board.likeCount -= 1
//                false
//            }
//
//            val boardDislikeEvaluation = boardEvaluationHistoryRepository.findByBoardIdAndUserIdAndIsDislikeTrue(board.boardId, userId)
//            if (boardDislikeEvaluation != null) {
//                boardEvaluationHistoryRepository.delete(boardDislikeEvaluation)
//                board.dislikeCount -= 1
//            }
//
//            return BoardEvaluationDTO(
//                isLike = isLike,
//                isDislike = false,
//            )
//        } else {
//            val boardDislikeEvaluation = boardEvaluationHistoryRepository.findByBoardIdAndUserIdAndIsDislikeTrue(board.boardId!!, userId)
//            val isDislike = if (boardDislikeEvaluation == null) {
//                boardEvaluationHistoryRepository.save(
//                    BoardEvaluationHistory(
//                        boardId = board.boardId,
//                        userId = userId,
//                        isLike = false,
//                        isDislike = true,
//                    )
//                )
//                board.dislikeCount += 1
//                true
//            } else {
//                boardEvaluationHistoryRepository.delete(boardDislikeEvaluation)
//                board.dislikeCount -= 1
//                false
//            }
//
//            val boardLikeEvaluation = boardEvaluationHistoryRepository.findByBoardIdAndUserIdAndIsLikeTrue(board.boardId, userId)
//            if (boardLikeEvaluation != null) {
//                boardEvaluationHistoryRepository.delete(boardLikeEvaluation)
//                board.likeCount -= 1
//            }
//
//            return BoardEvaluationDTO(
//                isLike = false,
//                isDislike = isDislike,
//            )
//        }
//    }

    fun getBoardContents(boardId: Long, userId: Long): BoardContentList {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()
        val boardContents = boardContentRepository.findAllByBoardId(board.boardId!!)
        val boardContentIds = boardContents.mapNotNull { it.boardContentId }

        val boardContentItems = boardContentItemRepository.findAllByBoardContentIdIn(boardContentIds)

        val boardContentResults =
            boardResultRepository.findAllByBoardContentItemIdIn(boardContentItems.mapNotNull { it.boardContentItemId })
                .groupingBy {
                    it.boardContentItemId
                }.eachCount()

        val boardContentMap = boardContentItems.map {
            it.toDTO(boardContentResults[it.boardContentItemId] ?: 0)
        }.groupBy { it.boardContentId }

        val isReviewExist = boardReviewRepository.existsByBoardIdAndUserId(board.boardId, userId)

        val boardContentsDTO = boardContents.map {
            it.toDTO(
                boardContentMap[it.boardContentId]!!,
            )
        }
        return BoardContentList(
            boardContentsDTO,
            isReviewExist
        )
    }

    @Transactional
    fun createBoardResult(boardId: Long, command: List<CreateBoardResultRequestCommand>, userId: Long): Long {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()

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

        return board.boardId!!
    }

    fun getBoardResult(boardId: Long): List<BoardResultDTO> {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()
        val boardContents = boardContentRepository.findAllByBoardId(boardId)
        val boardContentIds = boardContents.mapNotNull { it.boardContentId }

        val boardContentItems = boardContentItemRepository.findAllByBoardContentIdIn(boardContentIds)

        val boardContentResults =
            boardResultRepository.findAllByBoardContentItemIdIn(boardContentItems.mapNotNull { it.boardContentItemId })
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
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()
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
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()

        val boardResults = boardResultRepository.findAllByBoardIdAndUserId(boardId, userId)

//        if (boardResults.isEmpty()) {
//            throw NotJoinedGameException()
//        }
//
//        if (boardReviewRepository.findByBoardIdAndUserId(boardId, userId) != null) {
//            throw AlreadyExistReviewException()
//        }

        if (command.isLike) {
            board.likeCount += 1
        } else if (command.isDislike) {
            board.dislikeCount += 1
        }

        val boardReview = BoardReview(
            boardId = board.boardId!!,
            userId = userId,
            title = command.title,
            comment = command.comment,
            isLike = command.isLike,
            isDislike = command.isDislike,
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
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()
        val boardReview = boardReviewRepository.findById(command.boardReviewId).orElseThrow { NotFoundReviewException() }

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
    fun deleteBoardReview(userId: Long, boardId: Long, boardReviewId: Long) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()

        val boardReview = boardReviewRepository.findById(boardReviewId).orElseThrow { NotFoundReviewException() }

        if (boardReview.userId != userId) throw InvalidUserException()
        boardReviewRepository.delete(boardReview)

        if (boardReview.isLike) {
            board.likeCount -= 1
        } else if (boardReview.isDislike) {
            board.dislikeCount -= 1
        }

        boardReviewKeywordRepository.findAllByBoardReviewId(boardReview.boardReviewId!!)
            .let { boardReviewKeywordRepository.deleteAll(it) }
    }

    @Transactional
    fun createBoardReport(boardId: Long, userId: Long, content: String) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()

        BoardReport(
            boardId = board.boardId!!,
            userId = userId,
            content = content,
        ).let { boardReportRepository.save(it) }
    }

    @Transactional
    fun createBoardReviewReport(boardId: Long, boardReviewId: Long, userId: Long, content: String) {
        boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundBoardException()
        val boardReview = boardReviewRepository.findById(boardReviewId).orElseThrow { NotFoundReviewException() }

        BoardReviewReport(
            boardReviewId = boardReview.boardReviewId!!,
            userId = userId,
            content = content,
        ).let { boardReviewReportRepository.save(it) }
    }

//    fun getBoardReports(userId: Long): List<BoardReportDTO> {
//        return boardReportRepository.findAllByUserId(userId).map { it.toDTO() }
//    }
//
//    fun getBoardCommentReports(userId: Long): List<BoardCommentReportDTO> {
//        return boardCommentReportRepository.findAllByUserId(userId).map { it.toDTO() }
//    }

    fun todayRecommendGame(userId: Long?): SimpleBoardDTO {
        return userId?.let {
            val myBoardIds = boardRepository.findAllByUserId(it).map { it.boardId!! }
            val boardReportIds = boardReportRepository.findAllByUserId(it).map { it.boardId }
            println(myBoardIds)
            println(boardReportIds)
            boardRepository.todayRecommendGameByUserId(myBoardIds, boardReportIds).toSimpleBoard()
        } ?: run {
            boardRepository.todayRecommendGame().random().toSimpleBoard()
        }
    }

    fun getMyBoardCount(userId: Long): Int {
        return boardRepository.countBoardByUserId(userId)
    }

    fun getMyBoards(userId: Long): List<BoardListDTO> {
        val boards = boardRepository.findAllByUserId(userId)
        val boardKeywordMap = boardKeywordRepository.findAllByBoardIdIn(boards.mapNotNull { it.boardId })
            .groupBy { it.boardId }
            .mapValues { it.value.map { it.toDTO() } }

        return boards.map {
            it.toPageBoardDTO(
                boardKeywordMap[it.boardId] ?: emptyList()
            )
        }
    }

    fun relatedBoards(boardId: Long, userId: Long?): List<SimpleBoardDTO> {
        val board = boardRepository.findById(boardId).orElseThrow { NotFoundBoardException() }

        val boardReportIds = if (userId != null) {
            boardReportRepository.findAllByUserId(userId).map { it.boardId }
        } else {
            null
        }

        return boardRepository.relatedBoards(boardId, board.themeId, boardReportIds).map {
            it.toSimpleBoard()
        }
    }

    fun getReviews(boardId: Long, userId: Long?): List<BoardReviewDTO> {
        val board = boardRepository.findById(boardId).orElseThrow { NotFoundBoardException() }

        val boardReviewReportIds = if (userId != null) {
            boardReviewReportRepository.findAllByUserId(userId).map { it.boardReviewId }
        } else {
            null
        }

        val boardReviews = boardReviewRepository.search(board.boardId!!, boardReviewReportIds)

        val boardReviewIds = boardReviews.map { it.boardReviewId!! }
        val boardKeywordMap = boardReviewKeywordRepository.findAllByBoardReviewIdIn(boardReviewIds)
            .groupBy { it.boardReviewId }
            .mapValues { it.value.map { it.keyword } }

        val users = userRepository.findAllByUserIdIn(boardReviews.map { it.userId })
        val userNicknameMap = users.associate { it.userId to it.nickname }
        val userProfileMap = users.associate { it.userId to it.profileUrl }

        return boardReviews.map {
            it.toDTO(
                boardKeywordMap[it.boardReviewId] ?: emptyList(),
                userNicknameMap[it.userId]!!,
                userProfileMap[it.userId],
            )
        }
    }

    fun getParticipatedGames(userId: Long): List<ParticipatedBoardDTO> {
        val participatedBoardIds = boardResultRepository.findAllByUserId(userId).map { it.boardId }

        val boardKeywords = boardKeywordRepository.findAllByBoardIdIn(participatedBoardIds)
        val boardKeywordsMap = boardKeywords.groupBy { it.boardId }
            .mapValues { it.value.map { it.keyword } }

        return boardRepository.findByBoardIdIn(participatedBoardIds)
            .map {
                val isReviewExist = boardReviewRepository.existsByBoardIdAndUserId(it.boardId!!, userId)
                it.toParticipatedBoardDTO(isReviewExist, boardKeywordsMap[it.boardId]!!)
            }
    }

    fun getWroteReviews(userId: Long): List<BoardReviewDTO> {
        val wroteReviews = boardReviewRepository.findAllByUserId(userId)

        val boardReviewIds = wroteReviews.map { it.boardReviewId!! }
        val boardKeywordMap = boardReviewKeywordRepository.findAllByBoardReviewIdIn(boardReviewIds)
            .groupBy { it.boardReviewId }
            .mapValues { it.value.map { it.keyword } }

        val users = userRepository.findAllByUserIdIn(wroteReviews.map { it.userId })
        val userNicknameMap = users.associate { it.userId to it.nickname }
        val userProfileMap = users.associate { it.userId to it.profileUrl }

        return wroteReviews.map {
            it.toDTO(
                boardKeywordMap[it.boardReviewId] ?: emptyList(),
                userNicknameMap[it.userId]!!,
                userProfileMap[it.userId],
            )
        }
    }

    fun getRecommendReview(userId: Long?): List<BoardReviewDTO> {
        val recommendBoardReviews = userId?.let {
            val myBoardIds = boardRepository.findAllByUserId(userId).map { it.boardId!! }
            val myBoardReviewIds = boardReviewRepository.findAllByUserId(userId).map { it.boardReviewId!! }
            val boardReviewReportIds = boardReviewReportRepository.findAllByUserId(userId).map { it.boardReviewId }
            val boardReportIds = boardReportRepository.findAllByUserId(userId).map { it.boardId }
            boardReviewRepository.searchRecommendReviewByUserId(myBoardIds, myBoardReviewIds, boardReviewReportIds, boardReportIds)
        } ?: run {
            boardReviewRepository.searchRecommendReview()
        }

        val recommendBoardKeywordMap = boardReviewKeywordRepository.findAllByBoardReviewIdIn(recommendBoardReviews.map { it.boardReviewId!! })
            .groupBy { it.boardReviewId }
            .mapValues { it.value.map { it.keyword } }

        val users = userRepository.findAllByUserIdIn(recommendBoardReviews.map { it.userId })
        val userNicknameMap = users.associate { it.userId to it.nickname }
        val userProfileMap = users.associate { it.userId to it.profileUrl }

        return recommendBoardReviews.map {
            it.toDTO(
                recommendBoardKeywordMap[it.boardReviewId] ?: emptyList(),
                userNicknameMap[it.userId]!!,
                userProfileMap[it.userId],
            )
        }
    }

//    @Transactional
//    fun excelBoards(dataList: MutableList<ExcelBoardCommandDTO>) {
//        dataList.map { b ->
//            val board = boardRepository.save(
//                Board(
//                    introduce = b.introduce,
//                    title = b.title,
//                    userId = b.userId,
//                    themeId = b.themeId
//                )
//            )
//
//            var index = 0
//            println(board.introduce)
//            b.boardContents.map {
//                val boardContent = BoardContent(
//                    boardId = board.boardId!!,
//                    title = if (it.equals("이주훈")) null else it
//                )
//                val savedBoardContent = boardContentRepository.save(boardContent)
//                println(b.boardContentItems[index])
//                b.boardContentItems[index].split(",").map {
//                    BoardContentItem(
//                        boardContentId = savedBoardContent.boardContentId!!,
//                        item = it.trim()
//                    ).let { boardContentItemRepository.save(it) }
//                }
//                index += 1
//            }
//
//            b.keywords.map {
//                BoardKeyword(
//                    boardId = board.boardId!!,
//                    keyword = it
//                ).let { boardKeywordRepository.save(it) }
//            }
//        }
//    }
}
