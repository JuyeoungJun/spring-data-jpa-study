package study.datajpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    lateinit var memberJpaRepository: MemberJpaRepository

    @Test
    fun testMember() {
        val member = Member(userName = "memberA")
        val savedMember = memberJpaRepository.save(member)

        val find = memberJpaRepository.find(savedMember.id!!)

        assertThat(find.id).isEqualTo(savedMember.id)
        assertThat(find.userName).isEqualTo(savedMember.userName)
        assertThat(find).isEqualTo(member)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member(userName = "member1")
        val member2 = Member(userName = "member2")

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val findMember1 = memberJpaRepository.findById(member1.id!!).get()
        val findMember2 = memberJpaRepository.findById(member2.id!!).get()
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        val findAll = memberJpaRepository.findAll()
        assertThat(findAll.size).isEqualTo(2)

        val count = memberJpaRepository.count()
        assertThat(count).isEqualTo(2)

        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)

        val deletedCount = memberJpaRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThen() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 20)

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 15)

        assertThat(result?.get(0)?.userName).isEqualTo("AAA")
        assertThat(result?.get(0)?.age).isEqualTo(20)
        assertThat(result?.size).isEqualTo(1)

    }

    @Test
    fun testNamedQuery() {
        val member1 = Member(userName = "AAA", age = 10)
        val member2 = Member(userName = "AAA", age = 20)

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUserName("AAA")

        val findMember = result.first()
        assertThat(findMember).isEqualTo(member1)

    }

    @Test
    fun paging() {
        memberJpaRepository.save(Member(userName = "member1", age = 10))
        memberJpaRepository.save(Member(userName = "member2", age = 10))
        memberJpaRepository.save(Member(userName = "member3", age = 10))
        memberJpaRepository.save(Member(userName = "member4", age = 10))
        memberJpaRepository.save(Member(userName = "member5", age = 10))

        val age = 10
        val offset = 0
        val limit = 3

        val result = memberJpaRepository.findByPage(age, offset, limit)
        val totalCount = memberJpaRepository.totalCount(age)

        assertThat(result.size).isEqualTo(3)
        assertThat(totalCount).isEqualTo(5)
    }

    @Test
    fun bulkUpdate() {
        memberJpaRepository.save(Member(userName = "member1", age = 10))
        memberJpaRepository.save(Member(userName = "member2", age = 19))
        memberJpaRepository.save(Member(userName = "member3", age = 20))
        memberJpaRepository.save(Member(userName = "member4", age = 21))
        memberJpaRepository.save(Member(userName = "member5", age = 40))

        val resultCount = memberJpaRepository.bulkAgePlus(20)

        assertThat(resultCount).isEqualTo(3)
    }
}
