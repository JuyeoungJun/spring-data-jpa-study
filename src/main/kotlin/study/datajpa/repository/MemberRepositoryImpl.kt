package study.datajpa.repository

import study.datajpa.entity.Member
import javax.persistence.EntityManager

class MemberRepositoryImpl(
    private val em: EntityManager
) : MemberRepositoryCustom {

    override fun findMemberCustom(): MutableList<Member> {
        return em.createQuery("""
            select m from Member m
        """.trimIndent(), Member::class.java)
            .resultList

    }
}
