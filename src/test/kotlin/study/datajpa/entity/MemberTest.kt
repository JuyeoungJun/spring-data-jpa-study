package study.datajpa.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext lateinit var em: EntityManager

    @Test
    fun testEntity() {
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(userName = "member1", age = 10, team = teamA)
        val member2 = Member(userName = "member2", age = 20, team = teamA)
        val member3 = Member(userName = "member3", age = 30, team = teamB)
        val member4 = Member(userName = "member4", age = 40, team = teamB)

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        em.flush()
        em.clear()

        val members = em.createQuery(
            """
            select m from Member m
        """.trimIndent(), Member::class.java
        ).resultList

        members.forEach {
            println("member = $it")
            println("-> member.team = ${it.team}" )
        }

    }
}
