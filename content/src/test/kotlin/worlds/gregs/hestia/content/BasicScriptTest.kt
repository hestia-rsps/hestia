package worlds.gregs.hestia.content

import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.events.ButtonClick
import worlds.gregs.hestia.core.scripts.ScriptBase
import worlds.gregs.hestia.scripts.ScriptTesterMock

internal class BasicScriptTest : ScriptTesterMock<ScriptBase>() {

    @Test
    fun `first test`() {
        script.listeners.forEach {
            if(it.event == ButtonClick::class) {
                it.action.invoke(ButtonClick(0, 0, 0))
            }
        }
    }

}