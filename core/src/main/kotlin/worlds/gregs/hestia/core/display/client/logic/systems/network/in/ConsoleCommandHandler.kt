package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.perform
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.ConsoleCommand

class ConsoleCommandHandler : MessageHandlerSystem<ConsoleCommand>() {

    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: ConsoleCommand) {
        val (command) = message
        handle(entityId, command)
    }

    private fun handle(entityId: Int, command: String, parts: List<String> = command.split(" ")) {
        if(command.contains(" ")) {
            val prefix = parts[0]
            val content = command.removePrefix("$prefix ")
            es.perform(entityId, Command(prefix, content))
        } else {
            es.perform(entityId, Command(command, command))
        }
    }
}