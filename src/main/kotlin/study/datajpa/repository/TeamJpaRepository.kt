package study.datajpa.repository

import study.datajpa.entity.Team
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class TeamJpaRepository(
    @PersistenceContext private val em: EntityManager
) {
    fun save(team: Team): Team {
        em.persist(team)
        return team
    }

    fun delete(team: Team) {
        em.remove(team)
    }

    fun findAll(): List<Team> {
        return em.createQuery("""
            select t from Team t
        """.trimIndent(), Team::class.java).resultList

    }

    fun findById(id: Long): Optional<Team> {
        val team = em.find(Team::class.java, id)
        return Optional.ofNullable(team)
    }

    fun count(): Long? {
        return em.createQuery("""
            select count(t) from Team t
        """.trimIndent(), Long::class.javaObjectType).singleResult
    }
}
