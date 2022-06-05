package study.datajpa.dto

import study.datajpa.entity.Member

data class MemberDto(
    var id: Long? = null,
    var userName: String? = null,
    var teamName: String? = null
) {

    constructor(member: Member) : this(member.id!!, member.userName!!, member.team?.name!!) {
    }

    companion object {
        fun of(member: Member): MemberDto {
            return MemberDto(id = member.id, userName = member.userName, teamName = member.team?.name)
        }
    }
}
