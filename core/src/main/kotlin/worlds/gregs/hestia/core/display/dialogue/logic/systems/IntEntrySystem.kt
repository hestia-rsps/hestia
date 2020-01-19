package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.events.IntegerEntered
import worlds.gregs.hestia.core.display.dialogue.model.type.IntEntry
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.network.client.encoders.messages.Script

class IntEntrySystem : DialogueBaseSystem() {

    private lateinit var tasks: Tasks

    @Subscribe
    private fun intEntry(action: IntEntry) {
        val entityId = action.entity
        es.send(entityId, Script(109, action.title))
    }

    @Subscribe
    private fun handleInput(event: IntegerEntered) {
        val suspension = tasks.getSuspension(event.entity)
        if(suspension is IntEntry) {
            tasks.resume(event.entity, suspension, event.integer)
        } else {
            logger.warn("Unexpected int dialogue suspension task $suspension")
        }
    }

}