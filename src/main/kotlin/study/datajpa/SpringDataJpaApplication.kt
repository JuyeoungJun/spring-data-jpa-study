package study.datajpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Component
import java.util.*

@SpringBootApplication
@EnableJpaAuditing
class SpringDataJpaApplication

fun main(args: Array<String>) {
    runApplication<SpringDataJpaApplication>(*args)
}

@Component("beanbrokerAuditor")
class BeanBrokerAuditor : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of(UUID.randomUUID().toString())
    }
}
