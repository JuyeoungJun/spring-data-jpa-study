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

        val find = memberJpaRepository.find(savedMember.id)

        assertThat(find.id).isEqualTo(savedMember.id)
        assertThat(find.userName).isEqualTo(savedMember.userName)
        assertThat(find).isEqualTo(member)
    }


}
