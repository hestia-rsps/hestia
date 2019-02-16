package worlds.gregs.hestia.api.update

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.DisplayFlag

interface EntitySync {
    val localHandlers: HashMap<DisplayFlag, PacketBuilder.(Int, Int) -> Unit>

    fun addLocal(vararg stages: DisplayFlag, handler: PacketBuilder.(Int, Int) -> Unit) {
        stages.forEach { stage ->
            localHandlers[stage] = handler
        }
    }

    fun invokeLocal(packet: PacketBuilder, entityId: Int, local: Int, type: DisplayFlag) {
        localHandlers[type]?.invoke(packet, entityId, local)
    }
}