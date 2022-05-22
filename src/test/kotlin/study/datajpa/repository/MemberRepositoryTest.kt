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
}
