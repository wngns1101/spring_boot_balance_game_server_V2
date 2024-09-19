package balance_game_v2.domain.user.repository

import balance_game_v2.domain.user.entity.TermsAgreementHistory
import balance_game_v2.domain.user.model.TermsAgreementHistoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TermsAgreementHistoryRepository : JpaRepository<TermsAgreementHistory, Long> {
    fun findByUserIdAndType(userId: Long, type: TermsAgreementHistoryType): TermsAgreementHistory?
    fun deleteByUserId(userId: Long)
}
