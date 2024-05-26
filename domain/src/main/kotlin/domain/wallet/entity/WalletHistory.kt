package org.example.balance_game_v2.domain.wallet.entity

import domain.BaseEntity
import jakarta.persistence.*
import domain.wallet.model.WalletHistoryType


@Table(name = "wallet_history")
@Entity
class WalletHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val walletHistoryId: Long? = null,
    val walletId: Long,
    @Enumerated(EnumType.STRING)
    val type: WalletHistoryType,
    val amount: Long,
): BaseEntity()