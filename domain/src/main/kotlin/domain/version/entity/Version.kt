package domain.version.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "version")
@Entity
class Version(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val versionId: Long? = null,
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)
