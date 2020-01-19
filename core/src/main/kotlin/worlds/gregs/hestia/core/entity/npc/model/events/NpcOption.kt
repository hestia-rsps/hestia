package worlds.gregs.hestia.core.entity.npc.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * An [option] on [npc] selected by [entity]
 * @param npc The entity id of the npc
 * @param option The option selected
 */
data class NpcOption(val npc: Int, val option: String, val name: String) : EntityAction(), InstantEvent