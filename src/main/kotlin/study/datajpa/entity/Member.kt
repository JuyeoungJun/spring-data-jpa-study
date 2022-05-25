package study.datajpa.entity

import javax.persistence.*

@Entity
class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,
    var userName: String? = null,
    var age: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

) {
    fun changeTeam(team: Team) {
        this.team = team
        team.members += this
    }
}
