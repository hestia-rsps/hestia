package worlds.gregs.hestia.content

import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.window.model.events.ButtonClick
import worlds.gregs.hestia.core.script.ScriptBase
import worlds.gregs.hestia.script.ScriptMock

internal class BasicScriptTest : ScriptMock<ScriptBase>() {

    @Test
    fun `first test`() {
        script.listeners.forEach {
            if(it.event == ButtonClick::class) {
//                it.action.invoke(ButtonClick(0, 0, 0, ))
            }
        }
    }

}