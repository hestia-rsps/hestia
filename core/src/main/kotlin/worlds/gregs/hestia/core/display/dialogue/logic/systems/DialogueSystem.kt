package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.model.type.*
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ConfirmDestroy
import worlds.gregs.hestia.core.task.api.Tasks

class DialogueSystem : PassiveSystem() {

    private val logger = LoggerFactory.getLogger(DialogueSystem::class.java)!!
    private lateinit var tasks: Tasks

    @Subscribe
    private fun onContinue(action: ContinueDialogue) {
        when (val suspension = tasks.getSuspension(action.entity)) {
            is Destroy -> if(action.id == ConfirmDestroy) tasks.resume(action.entity, suspension, action.option == 3)
            is ItemBox -> tasks.resume(action.entity, suspension, Unit)
            is NpcChat -> tasks.resume(action.entity, suspension, Unit)
            is Options -> if(action.option in Dialogue.FIRST..Dialogue.FIFTH) tasks.resume(action.entity, suspension, action.option)
            is PlayerChat -> tasks.resume(action.entity, suspension, Unit)
            is Statement -> tasks.resume(action.entity, suspension, Unit)
            else -> {
                logger.warn("Unhandled continue $action")
            }
        }
    }

}