package worlds.gregs.hestia.game.api.update

import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.update.DisplayFlag

interface PlayerSync {
    val globalHandlers: HashMap<DisplayFlag, Packet.Builder.(Int, Int) -> Unit>

    fun addGlobal(vararg stages: DisplayFlag, handler: Packet.Builder.(Int, Int) -> Unit) {
        stages.forEach { stage ->
            globalHandlers[stage] = handler
        }
    }

    fun invokeGlobal(type: DisplayFlag, packet: Packet.Builder, entityId: Int, global: Int) {
        globalHandlers[type]?.invoke(packet, entityId, global)
    }
}