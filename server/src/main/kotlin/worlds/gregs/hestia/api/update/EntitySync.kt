package worlds.gregs.hestia.api.update

import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.update.DisplayFlag

interface EntitySync {
    val localHandlers: HashMap<DisplayFlag, Packet.Builder.(Int, Int) -> Unit>

    fun addLocal(vararg stages: DisplayFlag, handler: Packet.Builder.(Int, Int) -> Unit) {
        stages.forEach { stage ->
            localHandlers[stage] = handler
        }
    }

    fun invokeLocal(packet: Packet.Builder, entityId: Int, local: Int, type: DisplayFlag) {
        localHandlers[type]?.invoke(packet, entityId, local)
    }
}