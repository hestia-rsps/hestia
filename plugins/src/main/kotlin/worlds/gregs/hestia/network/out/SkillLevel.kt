package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.Skill

class SkillLevel(skill: Skill, level: Int, experience: Int) : Packet.Builder(93) {
    init {
        writeByteS(level)
        writeByteA(skill.ordinal)
        writeLEInt(experience)
    }
}