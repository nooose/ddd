package wolfdesk.ticket

import org.springframework.modulith.ApplicationModule

@ApplicationModule(
    allowedDependencies = [
        "base",
        "tenant :: integrate",
    ]
)
class TicketModule {
}
