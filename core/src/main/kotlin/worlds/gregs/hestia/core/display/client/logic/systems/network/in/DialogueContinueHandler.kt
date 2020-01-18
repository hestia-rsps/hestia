package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.DialogueContinue

@Wire(failOnNull = false)
class DialogueContinueHandler : MessageHandlerSystem<DialogueContinue>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var es: EventSystem

    override fun handle(entityId: Int, message: DialogueContinue) {
        val (hash, component) = message
        val id = hash shr 16
        var option = hash and 0xFF

        //Exception for two-options pressing '1' key
        if(option > 100) {
            option -= 100
        }

        es.perform(entityId, ContinueDialogue(id, option, component))
    }

}