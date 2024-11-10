package wolfdesk.ticket.ui.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wolfdesk.ticket.command.application.AiQuestionService

@RestController
class AiRestController(
    private val questionService: AiQuestionService,
) {

    @GetMapping("/ai")
    fun question(@RequestParam query: String?): String {
        return questionService.question(query ?: "")
    }
}
