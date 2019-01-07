package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class RunEnergy(energy: Int) : Packet.Builder(13) {
    init {
        writeByte(energy)
    }
}