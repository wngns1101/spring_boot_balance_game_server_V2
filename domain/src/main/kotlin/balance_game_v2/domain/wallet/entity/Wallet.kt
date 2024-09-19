package balance_game_v2.domain.wallet.entity

import balance_game_v2.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "wallet")
@Entity
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val walletId: Long? = null,
    val userId: Long,
    val amount: Long,
    val usedAmount: Long,
) : BaseEntity()
