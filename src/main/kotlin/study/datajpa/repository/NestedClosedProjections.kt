package study.datajpa.repository

interface NestedClosedProjections {

    fun getUserName(): String
    fun getTeam(): TeamInfo

    interface TeamInfo {
        fun getName(): String
    }
}
