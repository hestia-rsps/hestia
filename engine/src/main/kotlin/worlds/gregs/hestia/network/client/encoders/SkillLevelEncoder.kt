package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SKILL_LEVEL
import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel

class SkillLevelEncoder : MessageEncoder<SkillLevel>() {

    override fun encode(builder: PacketBuilder, message: SkillLevel) {
        val (skill, level, experience) = message
        builder.apply {
            writeOpcode(SKILL_LEVEL)
            writeByte(level, Modifier.SUBTRACT)
            writeByte(skill, Modifier.ADD)
            writeInt(experience, order = Endian.LITTLE)
        }
    }

}