package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Updates the players skill level & experience
 * @param skill The skills id
 * @param level The current players level
 * @param experience The current players experience
 */
data class SkillLevel(val skill: Int, val level: Int, val experience: Int) : Message