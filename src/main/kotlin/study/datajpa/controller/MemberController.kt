package study.datajpa.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import study.datajpa.repository.MemberRepository
import javax.annotation.PostConstruct

@RestController
class MemberController(
    private val memberRepository: MemberRepository
) {

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable("id") id: Long): String? {
        val member = memberRepository.findById(id).get()
        return member.userName
    }

    @GetMapping("/members2/{id}")
    fun findMember2(@PathVariable("id") member: Member): String? {
        return member.userName
    }

    @GetMapping("/members")
    fun list(@PageableDefault(size = 5) pageable: Pageable): Page<MemberDto> {
//        return memberRepository.findAll(pageable)
//            .map { MemberDto(id = it.id!!, userName = it.userName!!, teamName = it.team?.name!!) }

        return memberRepository.findAll(pageable)
            .map(MemberDto::of)
    }

//    @PostConstruct
//    fun init() {
//        for (i in (0..100)) {
//            memberRepository.save(Member(userName = "user $i"))
//
//        }
//    }

}
