package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.dialogue.model.type.StringEntry
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.network.client.encoders.messages.Script

class StringEntrySystem : DialogueBaseSystem() {

    private lateinit var tasks: Tasks

    @Subscribe
    private fun stringEntry(action: StringEntry) {
        val entityId = action.entity
        es.send(entityId, Script(108, action.title))
    }

    @Subscribe
    private fun handleInput(event: StringEntered) {
        val suspension = tasks.getSuspension(event.entity)
        if(suspension is StringEntry) {
            tasks.resume(event.entity, suspension, event.string)
        } else {
            logger.warn("Unexpected string dialogue suspension task $suspension")
        }
    }

}