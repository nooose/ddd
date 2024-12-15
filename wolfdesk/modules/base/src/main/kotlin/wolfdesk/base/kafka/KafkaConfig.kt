package wolfdesk.base.kafka

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConfig {
}
