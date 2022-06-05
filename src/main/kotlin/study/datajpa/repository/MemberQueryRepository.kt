package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import javax.persistence.EntityManager

@Repository
class MemberQueryRepository(
    private val em: EntityManager
) {

    fun findAllMembers(): MutableList<Member>? {
        return em.createQuery("""
            select m from Member m
        """.trimIndent(), Member::class.java).resultList

    }

}
