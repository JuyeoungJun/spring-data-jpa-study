package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import java.util.Optional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberJpaRepository(
    @PersistenceContext
    private val em: EntityManager
) {

    fun save(member: Member): Member {
        em.persist(member)
        return member
    }
    fun delete(member: Member) {
        em.remove(member)
    }

    fun find(id: Long): Member {
        return em.find(Member::class.java, id)
    }

    fun findAll(): List<Member> {
        return em.createQuery("""
            select m from Member m
        """.trimIndent(), Member::class.java).resultList
    }

    fun findById(id: Long): Optional<Member> {
        val member = em.find(Member::class.java, id)
        return Optional.ofNullable(member)
    }

    fun count(): Long? {
        return em.createQuery("""
            select count(m) from Member m
        """.trimIndent(), Long::class.javaObjectType).singleResult
    }

    fun findByUsernameAndAgeGreaterThen(userName: String, age: Int): MutableList<Member>? {
        return em.createQuery("""
            select m from Member m where m.userName = :userName and m.age > :age
        """.trimIndent(), Member::class.java)
            .setParameter("userName", userName)
            .setParameter("age", age)
            .resultList
    }

    fun findByUserName(userName: String): MutableList<Member> {
        return em.createNamedQuery("Member.findByUserName", Member::class.java)
            .setParameter("userName", userName)
            .resultList
    }

    fun findByPage(age: Int, offSet: Int, limit: Int): MutableList<Member> {
        return em.createQuery("""
            select m from Member m 
            where m.age = :age
            order by m.userName desc 
        """.trimIndent(), Member::class.java)
            .setParameter("age", age)
            .setFirstResult(offSet)
            .setMaxResults(limit)
            .resultList
    }

    fun totalCount(age: Int): Long? {
        return em.createQuery("""
            select count(m) from Member m 
            where m.age = :age
        """.trimIndent(), Long::class.javaObjectType)
            .setParameter("age", age)
            .singleResult
    }

    fun bulkAgePlus(age: Int): Int {
        return em.createQuery("""
            update Member m set m.age = m.age + 1
            where m.age >= :age
        """.trimIndent())
            .setParameter("age", age)
            .executeUpdate()
    }

}
