package study.datajpa.repository

import study.datajpa.entity.Member

interface MemberRepositoryCustom {
    fun findMemberCustom(): List<Member>
}
