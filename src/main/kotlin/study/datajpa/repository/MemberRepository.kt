package study.datajpa.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUserNameAndAgeGreaterThan(userName: String, age: Int): MutableList<Member>

    fun findTop3By(): MutableList<Member>

    fun findByUserName(@Param("userName") userName: String): MutableList<Member>

    @Query("select m from Member m where m.userName = :userName and m.age = :age")
    fun findUser(@Param("userName") userName: String, @Param("age") age: Int): MutableList<Member>

    @Query("select m.userName from Member m")
    fun findUserNameList(): MutableList<String>

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.userName, t.name) from Member m join m.team t")
    fun findMemberDto(): MutableList<MemberDto>

    @Query("select m from Member m where m.userName in :names")
    fun findByNames(@Param("names") names: MutableList<String>): MutableList<Member>

    fun findListByUserName(userName: String): MutableList<Member>
    fun findMemberByUserName(userName: String): Member?

    fun findOptionalByUserName(userName: String): Optional<Member>

//    @Query(value = "select m from Member m left join m.team t",
//        countQuery = "select count(m.userName) from Member m")
    fun findByAge(age: Int, pageable: Pageable): Page<Member>

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    fun bulkAgePlus(@Param("age") age: Int): Int

    @Query("select m from Member m left join fetch m.team")
    fun findMemberFetchJoin(): MutableList<Member>

    @EntityGraph(attributePaths = ["team"])
    override fun findAll(): MutableList<Member>

    @EntityGraph(attributePaths = ["team"])
    @Query("select m from Member m")
    fun findMemberEntityGraph(): MutableList<Member>

//    @EntityGraph("Member.all")
    @EntityGraph(attributePaths = ["team"])
    fun findEntityGraphByUserName(@Param("userName") userName: String): MutableList<Member>

    @QueryHints(value = [QueryHint(name = "org.hibernate.readOnly", value = "true")])
    fun findReadOnlyByUserName(userName: String): Member?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findLockByUserName(userName: String): MutableList<Member>
}
