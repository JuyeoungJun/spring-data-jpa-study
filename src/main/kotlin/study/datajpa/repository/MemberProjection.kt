package study.datajpa.repository

interface MemberProjection {
    fun getId(): Long?
    fun getUserName(): String?
    fun getTeamName(): String?

}
