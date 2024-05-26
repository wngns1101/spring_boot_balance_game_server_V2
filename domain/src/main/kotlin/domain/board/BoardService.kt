package domain.board

import domain.auth.exception.NotFoundUserException
import domain.board.dto.*
import domain.board.repository.BoardContentRepository
import domain.board.repository.BoardRepository
import domain.board.entity.Board
import domain.board.entity.BoardContent
import domain.board.exception.NotFoundException
import domain.board.model.BoardSortCondition
import domain.user.UserService
import domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService (
    val userRepository: UserRepository,
    val boardRepository: BoardRepository,
    val boardContentRepository: BoardContentRepository,
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
            totalPage = boards.size,
        )
    }

    @Transactional
    fun getBoardDetail(boardId: Long): BoardDetailDTO {
        val board = boardRepository.findByBoardIdAndDeletedAtIsNull(boardId) ?: throw NotFoundException()
        board.viewCount += 1

        val writer = userRepository.findById(board.userId).orElseThrow { NotFoundUserException() }

        return board.toBoardDetail(
            WriterDTO(
                writer.userId!!,
                writer.nickname,
            )
        )
    }
}
