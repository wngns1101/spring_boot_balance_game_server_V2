package balance_game_v2.domain.user.entity

import balance_game_v2.domain.BaseEntity
import balance_game_v2.domain.user.model.TermsAgreementHistoryType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "terms_agreement_history")
@Entity
class TermsAgreementHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val termsAgreementHistoryId: Long? = null,
    val userId: Long,
    @Enumerated(EnumType.STRING)
    val type: TermsAgreementHistoryType,
    val status: Boolean,
) : BaseEntity()
