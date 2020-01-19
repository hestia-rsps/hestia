package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.Options
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi5Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar5
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceVisibility

class OptionsSystem : DialogueBaseSystem() {

    @Subscribe
    private fun options(action: Options) {
        val entityId = action.entity
        val multilineTitle = action.title?.contains("<br>") == true
        val multilineOptions = action.options.any { it.contains("<br>") }
        val optionCount = action.options.size
        //Choose interface
        val id = when (optionCount) {
            2 -> if (multilineTitle) MultiVar2 else if (multilineOptions) Multi2Chat else Multi2
            3 -> if (multilineTitle) MultiVar3 else if (multilineOptions) Multi3Chat else Multi3
            4 -> if (multilineTitle) MultiVar4 else if (multilineOptions) Multi4Chat else Multi4
            5 -> if (multilineTitle) MultiVar5 else if (multilineOptions) Multi5Chat else Multi5
            else -> return logger.warn("Invalid multi option $action")
        }
        //Open
        openDialogue(entityId, id)
        val startIndex = if (multilineTitle) 0 else 1
        if (action.title != null) {
            //Update sword locations
            val large = action.title.length > 30//Approximation
            es.send(entityId, InterfaceVisibility(id, if (multilineTitle) 3 + optionCount else 1 + optionCount + 3, large))
            es.send(entityId, InterfaceVisibility(id, if (multilineTitle) 4 + optionCount else 9 + optionCount.rem(2), !large))
            //Send title
            es.send(entityId, InterfaceComponentText(id, startIndex, action.title))
        }
        //Send options
        action.options.forEachIndexed { index, option ->
            es.send(entityId, InterfaceComponentText(id, startIndex + 1 + index, option))
        }
    }
}