package wolfdesk.ticket

import org.springframework.modulith.ApplicationModule

@ApplicationModule(
    allowedDependencies = ["base", "tenant::tenant-integrate"]
)
class TicketModule {
}
