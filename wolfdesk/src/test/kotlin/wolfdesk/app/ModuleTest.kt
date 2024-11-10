package wolfdesk.app

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter
import wolfdesk.WolfdeskApplication

class ModuleTest {

    @Test
    fun `모듈 테스트`() {
        val modules = ApplicationModules.of(WolfdeskApplication::class.java)
        modules.forEach { println(it) }
        modules.verify()

        Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }
}
