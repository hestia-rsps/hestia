package worlds.gregs.hestia.core.entity.player.model.events

import worlds.gregs.hestia.core.script.dsl.task.PlayerOptions
import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * Context menu option
 * @param entity The entity doing the action
 * @param target The target player
 * @param option The selected option
 */
data class PlayerOption(override val entity: Int, override val target: Int, val option: PlayerOptions) : TargetEvent