package domain.board

import domain.auth.exception.NotFoundUserException
import domain.board.dto.BoardCommentReportDTO
import domain.board.dto.BoardContentDTO
import domain.board.dto.BoardDetailDTO
import domain.board.dto.BoardReportDTO
import domain.board.dto.BoardResultDTO
import domain.board.dto.CreateBoardCommand
import domain.board.dto.CreateBoardCommentCommand
import domain.board.dto.DeleteBoardCommentCommand
import domain.board.dto.ModifyBoardCommand
import domain.board.dto.ModifyBoardContentCommand
import domain.board.dto.PageBoardDTO
import domain.board.dto.WriterDTO
import domain.board.dto.toBoardDetail
import domain.board.dto.toDTO
import domain.board.dto.toPageBoardDTO
import domain.board.entity.Board
import domain.board.entity.BoardComment
import domain.board.entity.BoardCommentReport
import domain.board.entity.BoardContent
import domain.board.entity.BoardHeart
import domain.board.entity.BoardReport
import domain.board.entity.BoardResult
import domain.board.exception.NotFoundException
import domain.board.model.BoardSortCondition
import domain.board.repository.BoardCommentReportRepository
import domain.board.repository.BoardCommentRepository
import domain.board.repository.BoardContentRepository
import domain.board.repository.BoardHeartRepository
import domain.board.repository.BoardReportRepository
import domain.board.repository.BoardRepository
import domain.board.repository.BoardResultRepository
import domain.error.InvalidUserException
import domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    val userRepository: UserRepository,
    val boardRepository: BoardRepository,
    val boardContentRepository: BoardContentRepository,
    val boardHeartRepository: BoardHeartRepository,
    val boardResultRepository: BoardResultRepository,
    val boardCommentRepository: BoardCommentRepository,
    val boardReportRepository: BoardReportRepository,
    val boardCommentReportRepository: BoardCommentReportRepository,
) {
    @Transactional
    fun createBoard(userId: Long, command: CreateBoardCommand) {
        val savedBoard = Board(
            userId = userId,
            title = command.title,
            content = command.content,
        ).let {
            boardRepository.save(it)
        }

        command.boardContent.map {
            BoardContent(
                boardId = savedBoard.boardId!!,
                title = it.title,
                photoUrl = it.photoUrl,
            )
        }.let {
            boardContentRepository.saveAll(it)
        }
    }

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?): PageBoardDTO {
        val pageable = PageRequest.of(page, size)
        val boards = boardRepository.search(query, pageable, sortCondition)

        return PageBoardDTO(
            boards = boards.content.map { it.toPageBoardDTO() },
            totalPage = boards.totalPages,
        )
    }

    @Transactional
    fun getBoardDetail(boardId: Long): BoardDetailDTO {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        board.viewCount += 1

        val writer = userRepository.findById(board.userId).orElseThrow { NotFoundUserException() }

        val boardComment = boardCommentRepository.findAllByBoardId(board.boardId!!).map { it.toDTO() }

        return board.toBoardDetail(
            WriterDTO(
                writer.userId!!,
                writer.nickname,
            ),
            boardComment
        )
    }

    @Transactional
    fun boardHeart(boardId: Long, userId: Long): Boolean {
        val board = boardRepository.findById(boardId).orElseThrow { NotFoundException() }
        val exist = boardHeartRepository.existsByBoardIdAndUserId(boardId, userId)

        if (exist) {
            val boardHeart = boardHeartRepository.findByBoardIdAndUserId(boardId, userId)
            boardHeartRepository.delete(boardHeart)

            board.heartCount -= 1
            return false
        } else {
            BoardHeart(
                boardId = boardId,
                userId = userId,
            ).let { boardHeartRepository.save(it) }

            board.heartCount += 1
            return true
        }
    }

    fun getBoardContents(boardId: Long): List<BoardContentDTO> {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        val boardContents = boardContentRepository.findAllByBoardId(boardId)

        return boardContents.map { it.toDTO() }
    }

    @Transactional
    fun createBoardResult(boardId: Long, boardContentId: Long, userId: Long) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()

        BoardResult(
            boardId = board.boardId!!,
            boardContentId = boardContentId,
            userId = userId
        ).let { boardResultRepository.save(it) }
    }

    fun getBoardResult(boardId: Long): List<BoardResultDTO> {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()

        val results = boardResultRepository.findAllByBoardId(board.boardId!!).groupBy { it.boardContentId }.mapValues { it.value.size }

        return results.map {
            BoardResultDTO(
                boardContentId = it.key,
                count = it.value
            )
        }
    }

    @Transactional
    fun modifyBoard(boardId: Long, command: ModifyBoardCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        board.title = command.title
        board.content = command.content

        val boardContents = boardContentRepository.findAllByBoardId(board.boardId!!)
        boardContentRepository.deleteAll(boardContents)

        command.boardContent.map {
            BoardContent(
                boardId = board.boardId,
                title = it.title,
                photoUrl = it.photoUrl,
            )
        }.let { boardContentRepository.saveAll(it) }
    }

    @Transactional
    fun createBoardComment(boardId: Long, userId: Long, command: CreateBoardCommentCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()

        BoardComment(
            boardId = board.boardId!!,
            userId = userId,
            parentCommentId = command.parentCommentId,
            comment = command.comment
        ).let { boardCommentRepository.save(it) }
    }

    @Transactional
    fun modifyBoardComment(boardId: Long, userId: Long, command: ModifyBoardContentCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        val boardComment = boardCommentRepository.findById(command.commentId).orElseThrow { NotFoundException() }

        if (boardComment.userId != userId) throw InvalidUserException()

        boardCommentRepository.delete(boardComment)
        BoardComment(
            boardId = board.boardId!!,
            userId = userId,
            parentCommentId = boardComment.parentCommentId,
            comment = command.content
        ).let { boardCommentRepository.save(it) }
    }

    @Transactional
    fun deleteBoardComment(boardId: Long, userId: Long, command: DeleteBoardCommentCommand) {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        val boardComment = boardCommentRepository.findById(command.commentId).orElseThrow { NotFoundException() }

        if (boardComment.userId != userId) throw InvalidUserException()
        boardCommentRepository.delete(boardComment)
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
        val boardComment = boardCommentRepository.findById(boardCommentId).orElseThrow { NotFoundException() }

        BoardCommentReport(
            boardCommentId = boardComment.boardId,
            userId = userId,
        ).let { boardCommentReportRepository.save(it) }
    }

    fun getBoardReports(userId: Long): List<BoardReportDTO> {
        return boardReportRepository.findAllByUserId(userId).map { it.toDTO() }
    }

    fun getBoardCommentReports(userId: Long): List<BoardCommentReportDTO> {
        return boardCommentReportRepository.findAllByUserId(userId).map { it.toDTO() }
    }
}
