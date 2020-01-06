package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

/**
 * Experience to award to [entity]
 * @param skill The skill to award the experience
 * @param increase The base-unit amount of experience to award
 */
data class Experience(val skill: Skill, val increase: Int) : Action(), InstantEvent