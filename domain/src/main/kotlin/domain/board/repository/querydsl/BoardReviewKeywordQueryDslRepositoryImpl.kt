package domain.board.repository.querydsl

import domain.board.entity.BoardReviewKeyword
import domain.board.entity.QBoardReviewKeyword.boardReviewKeyword
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BoardReviewKeywordQueryDslRepositoryImpl : BoardReviewKeywordQueryDslRepository, QuerydslRepositorySupport(BoardReviewKeyword::class.java) {
    override fun previewBoardReviewKeyword(boardReviewIds: List<Long>): List<BoardReviewKeyword> {
        val results = from(boardReviewKeyword)
            .where(boardReviewKeyword.boardReviewId.`in`(boardReviewIds))
            .groupBy(boardReviewKeyword.keyword)
            .select(
                boardReviewKeyword.keyword,
                boardReviewKeyword.keyword.count().`as`("keywordCount")
            )
            .orderBy(boardReviewKeyword.keyword.count().desc())
            .limit(3)
            .fetch()

        return results.map { tuple ->
            val keyword = tuple.get(boardReviewKeyword.keyword)
            val userId = tuple.get(boardReviewKeyword.userId)
            val boardReviewId = tuple.get(boardReviewKeyword.boardReviewId)
            val boardReviewKeywordId = tuple.get(boardReviewKeyword.boardReviewKeywordId)
            // 이 부분에서 BoardReviewKeyword 객체를 생성하는 로직 필요
            // 예시로, keyword만 설정한 객체를 생성한다고 가정
            BoardReviewKeyword(
                boardReviewKeywordId = boardReviewKeywordId,
                boardReviewId = boardReviewId!!,
                userId = userId!!,
                keyword = keyword!!,
            )
        }
    }
}
