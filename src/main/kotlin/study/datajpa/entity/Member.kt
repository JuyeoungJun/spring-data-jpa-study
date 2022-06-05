package study.datajpa.entity

import javax.persistence.*

@Entity
//@NamedQuery(
//    name = "Member.findByUserName",
//    query = "select m from Member m where m.userName = :userName"
//)
//@NamedEntityGraph(
//    name = "Member.all",
//    attributeNodes = [NamedAttributeNode("team")]
//)
class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,
    var userName: String? = null,
    var age: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null
) : BaseEntity() {
    fun changeTeam(team: Team) {
        this.team = team
        team.members += this
    }
}
