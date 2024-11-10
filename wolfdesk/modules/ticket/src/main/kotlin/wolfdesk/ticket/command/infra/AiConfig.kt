package wolfdesk.ticket.command.infra

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!test")
@Configuration
class AiConfig {

    @Bean
    fun chatClient(model: ChatModel, vectorStore: VectorStore): ChatClient {
        return ChatClient.builder(model)
            .defaultAdvisors(QuestionAnswerAdvisor(vectorStore))
            .build()
    }

    @Bean
    fun vectorStore(model: EmbeddingModel): SimpleVectorStore {
        return SimpleVectorStore(model)
    }
}
