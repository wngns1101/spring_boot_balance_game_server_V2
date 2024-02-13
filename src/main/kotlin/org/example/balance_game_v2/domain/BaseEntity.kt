package org.example.balance_game_v2.domain

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.MIN

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.MIN

    var deletedAt: LocalDateTime? = null

    fun delete(){
        deletedAt = LocalDateTime.now()
    }
}