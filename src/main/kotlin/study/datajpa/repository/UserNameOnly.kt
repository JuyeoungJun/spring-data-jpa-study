package study.datajpa.repository

import org.springframework.beans.factory.annotation.Value

interface UserNameOnly {
    @get:Value("#{target.userName + ' ' + target.age}")
    val userName: String
}
