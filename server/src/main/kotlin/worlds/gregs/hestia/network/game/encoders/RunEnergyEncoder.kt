package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.RUN_ENERGY
import worlds.gregs.hestia.network.game.encoders.messages.RunEnergy

class RunEnergyEncoder : MessageEncoder<RunEnergy>() {

    override fun encode(builder: PacketBuilder, message: RunEnergy) {
        val (energy) = message
        builder.apply {
            writeOpcode(RUN_ENERGY)
            writeByte(energy)
        }
    }

}