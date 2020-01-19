package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.Window

abstract class DialogueBaseSystem : PassiveSystem() {

    internal val logger = LoggerFactory.getLogger(DialogueBaseSystem::class.java)!!
    internal lateinit var es: EventSystem
    internal lateinit var interfaces: Interfaces

    internal fun openDialogue(entityId: Int, id: Int) {
        interfaces.closeWindow(entityId, Window.DIALOGUE_BOX)
        interfaces.openInterface(entityId, id)
    }

}