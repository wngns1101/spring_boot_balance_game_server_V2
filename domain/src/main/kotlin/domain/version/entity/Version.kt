package domain.version.entity

import jakarta.persistence.*

@Table(name = "version")
@Entity
class Version (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val version: Long? = null,
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)