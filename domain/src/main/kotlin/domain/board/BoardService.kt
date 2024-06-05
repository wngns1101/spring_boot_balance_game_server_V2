package domain.board

import domain.auth.exception.NotFoundUserException
import domain.board.dto.*
import domain.board.entity.*
import domain.board.exception.NotFoundException
import domain.board.model.BoardSortCondition
import domain.board.repository.*
import domain.error.InvalidUserException
import domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService (
    val userRepository: UserRepository,
    val boardRepository: BoardRepository,
    val boardContentRepository: BoardContentRepository,
    val boardHeartRepository: BoardHeartRepository,
    val boardResultRepository: BoardResultRepository,
    val boardCommentRepository: BoardCommentRepository,
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

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?): PageBoardDTO  {
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

}
