package study.datajpa.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import study.datajpa.entity.Team
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var teamRepository: TeamRepository

    @PersistenceContext
    lateinit var em: EntityManager

    @Test
    fun testMember() {
        val member = Member(userName = "memberA")
        val savedMember = memberRepository.save(member)

        val find = memberRepository.findById(savedMember.id ?: throw Exception()).get()

        Assertions.assertThat(find.id).isEqualTo(savedMember.id)
        Assertions.assertThat(find.userName).isEqualTo(savedMember.userName)
        Assertions.assertThat(find).isEqualTo(member)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member(userName = "member1")
        val member2 = Member(userName = "member2")

        memberRepository.save(member1)
        memberRepository.save(member2)

        val findMember1 = memberRepository.findById(member1.id!!).get()
        val findMember2 = memberRepository.findById(member2.id!!).get()
        Assertions.assertThat(findMember1).isEqualTo(member1)
        Assertions.assertThat(findMember2).isEqualTo(member2)

        val findAll = memberRepository.findAll()
        Assertions.assertThat(findAll.size).isEqualTo(2)

        val count = memberRepository.count()
        Assertions.assertThat(count).isEqualTo(2)

        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val deletedCount = memberRepository.count()
        Assertions.assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThen() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUserNameAndAgeGreaterThan("AAA", 15)

        Assertions.assertThat(result?.get(0)?.userName).isEqualTo("AAA")
        Assertions.assertThat(result?.get(0)?.age).isEqualTo(20)
        Assertions.assertThat(result?.size).isEqualTo(1)
    }

    @Test
    fun findTop3By() {
        memberRepository.findTop3By()
    }

    @Test
    fun testNamedQuery() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUserName("AAA")

        val findMember = result.first()
        Assertions.assertThat(findMember).isEqualTo(member1)

    }

    @Test
    fun testQuery() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUser("AAA", 10)

        Assertions.assertThat(result.first()).isEqualTo(member1)
    }

    @Test
    fun testUserNameList() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUserNameList()
        for (s in result) {
            println("s = $s")
        }
    }

    @Test
    fun testMemberDto() {
        val team = Team(name = "teamA")
        teamRepository.save(team)

        val member1 = Member(userName = "AAA", age = 10)
        member1.team = team
        memberRepository.save(member1)


        val result = memberRepository.findMemberDto()
        for (memberDto in result) {
            println("memberDto = $memberDto")
        }
    }

    @Test
    fun testFindByNames() {
        val team = Team(name = "teamA")
        teamRepository.save(team)

        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 10)
        member1.team = team
        memberRepository.save(member1)
        memberRepository.save(member2)



        val result = memberRepository.findByNames(mutableListOf("AAA", "BBB"))
        for (member in result) {
            println("member = $member")
        }
    }

    @Test
    fun returnType() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 10)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val result1 = memberRepository.findListByUserName("AAA")
        val result2 = memberRepository.findMemberByUserName("AAA")
        val result3 = memberRepository.findOptionalByUserName("AAA")
    }

    @Test
    fun paging() {
        memberRepository.save(Member(userName = "member1", age = 10))
        memberRepository.save(Member(userName = "member2", age = 10))
        memberRepository.save(Member(userName = "member3", age = 10))
        memberRepository.save(Member(userName = "member4", age = 10))
        memberRepository.save(Member(userName = "member5", age = 10))

        val age = 10
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"))

        val page = memberRepository.findByAge(age, pageRequest)

        val memberDtos = page.map {
            MemberDto(id = it.id!!, userName = it.userName!!, teamName = "")
        }

        for (memberDto in memberDtos) {
            println(memberDto)
        }

        val content = page.content

        Assertions.assertThat(content.size).isEqualTo(3)
        Assertions.assertThat(page.number).isEqualTo(0)
        Assertions.assertThat(page.isFirst).isEqualTo(true)
        Assertions.assertThat(page.hasNext()).isEqualTo(true)
    }

    @Test
    fun bulkUpdate() {
        memberRepository.save(Member(userName = "member1", age = 10))
        memberRepository.save(Member(userName = "member2", age = 19))
        memberRepository.save(Member(userName = "member3", age = 20))
        memberRepository.save(Member(userName = "member4", age = 21))
        memberRepository.save(Member(userName = "member5", age = 40))

        val resultCount = memberRepository.bulkAgePlus(20)
//        em.flush()
//        em.clear()

        val result = memberRepository.findByUserName("member5")
        println("age = ${result[0].age}")

        Assertions.assertThat(resultCount).isEqualTo(3)
    }

    @Test
    fun findMemberLazy() {
        // given
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        teamRepository.save(teamA)
        teamRepository.save(teamB)

        val member1 = Member(userName = "member1", age = 10, team = teamA)
        val member2 = Member(userName = "member2", age = 10, team = teamB)
        memberRepository.save(member1)
        memberRepository.save(member2)

        em.flush()
        em.clear()

        // when
        val members = memberRepository.findEntityGraphByUserName("member1")

        for (member in members) {
            println("name = ${member.userName}")
            println("member.team = ${member.team?.name}")
        }

    }

    @Test
    fun queryHint() {
        val member1 = memberRepository.save(Member(userName = "member1", age = 10))
        em.flush()
        em.clear()

//        val findMember = member1.id?.let { memberRepository.findById(it) }?.get()
        val findMember = memberRepository.findReadOnlyByUserName("member1")
        findMember?.userName = "member2"

        em.flush()
    }

    @Test
    fun lock() {
        val member1 = memberRepository.save(Member(userName = "member1", age = 10))
        em.flush()
        em.clear()

//        val findMember = member1.id?.let { memberRepository.findById(it) }?.get()
        val findMember = memberRepository.findLockByUserName("member1")

    }
}
