package worlds.gregs.hestia.core.display.client.logic.systems

import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.events.Chat

private typealias ChatPacket = world.gregs.hestia.core.network.protocol.encoders.messages.Chat

class ChatSystem : PassiveSystem() {

    private lateinit var es: EventSystem

    @Subscribe
    private fun handle(chat: Chat) {
        es.send(chat.entity, ChatPacket(chat.type, chat.tile, chat.name, chat.message))
    }
}