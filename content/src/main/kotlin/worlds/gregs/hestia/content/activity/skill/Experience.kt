package worlds.gregs.hestia.content.activity.skill

import net.mostlyoriginal.api.event.common.Cancellable
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.EntityEvent

/**
 * Experience to award to [entity]
 * @param skill The skill to award the experience
 * @param increase The base-unit amount of experience to award
 */
data class Experience(override val entity: Int, val skill: Skill, val increase: Int) : EntityEvent, InstantEvent, Cancellable {

    private var cancelled = false

    override fun setCancelled(value: Boolean) {
        cancelled = value
    }

    override fun isCancelled() = cancelled

}