package study.datajpa.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
open class JpaBaseEntity {

    @Column(updatable = false)
    open var createdDate: LocalDateTime? = null
    open var updatedDate: LocalDateTime? = null

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        this.createdDate = now
        this.updatedDate = now
    }

    @PreUpdate
    fun preUpdate() {
        val now = LocalDateTime.now()
        this.updatedDate = now
    }



}
