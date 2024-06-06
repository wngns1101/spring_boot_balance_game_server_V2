package domain.user.repository

import domain.user.entity.TermsAgreementHistory
import domain.user.model.TermsAgreementHistoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TermsAgreementHistoryRepository : JpaRepository<TermsAgreementHistory, Long> {
    fun findByUserIdAndType(userId: Long, type: TermsAgreementHistoryType): TermsAgreementHistory?
}
