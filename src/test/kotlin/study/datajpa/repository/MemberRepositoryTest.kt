package study.datajpa.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

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

}
