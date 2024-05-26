package domain.wallet.entity

import domain.BaseEntity
import jakarta.persistence.*

@Table(name = "wallet")
@Entity
class Wallet (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val walletId: Long? = null,
    val userId: Long,
    val amount: Long,
    val usedAmount: Long,
): BaseEntity()