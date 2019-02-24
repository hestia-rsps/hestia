package worlds.gregs.hestia.api.update

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.DisplayFlag

interface PlayerSync {
    val globalHandlers: HashMap<DisplayFlag, PacketBuilder.(Int, Int) -> Unit>

    fun addGlobal(vararg stages: DisplayFlag, handler: PacketBuilder.(Int, Int) -> Unit) {
        stages.forEach { stage ->
            globalHandlers[stage] = handler
        }
    }

    fun invokeGlobal(type: DisplayFlag, packet: PacketBuilder, entityId: Int, global: Int) {
        globalHandlers[type]?.invoke(packet, entityId, global)
    }
}