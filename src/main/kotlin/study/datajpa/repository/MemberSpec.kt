package study.datajpa.repository

import org.springframework.data.jpa.domain.Specification
import org.springframework.util.StringUtils
import study.datajpa.entity.Member
import study.datajpa.entity.Team
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root
import javax.print.attribute.standard.RequestingUserName

class MemberSpec {

    companion object {
        fun teamName(teamName: String?): Specification<Member?> {
            return (Specification { root: Root<Member?>, query: CriteriaQuery<*>?, builder: CriteriaBuilder ->
                if (StringUtils.isEmpty(teamName)) {
                    return@Specification null
                }
                val t = root.join<Member, Team>("team", JoinType.INNER)
                builder.equal(t.get<Any>("name"), teamName)
            })
        }
    }

}
