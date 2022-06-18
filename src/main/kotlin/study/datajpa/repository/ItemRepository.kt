package study.datajpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import study.datajpa.entity.Item

@Repository
interface ItemRepository : JpaRepository<Item, Long> {
}
