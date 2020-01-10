package worlds.gregs.hestia.core.entity.mob.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * An [option] on [target] selected by [entity]
 * @param target The entity id of the mob
 * @param option The option selected
 */
data class MobOption(val target: Int, val option: String, val name: String) : EntityAction(), InstantEvent