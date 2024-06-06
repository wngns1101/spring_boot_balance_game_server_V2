package org.example.balance_game_v2.domain.wallet.entity

import domain.BaseEntity
import domain.wallet.model.WalletHistoryType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "wallet_history")
@Entity
class WalletHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val walletHistoryId: Long? = null,
    val walletId: Long,
    @Enumerated(EnumType.STRING)
    val type: WalletHistoryType,
    val amount: Long,
) : BaseEntity()
