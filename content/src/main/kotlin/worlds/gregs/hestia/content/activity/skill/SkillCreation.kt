package worlds.gregs.hestia.content.activity.skill

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.task.api.TaskType

/**
 * Sends a dialogue to select skill item and amounts from a list
 * @param items The list of skilling items to send
 * @param type The selection type @see [SkillCreation_script] or 1111.cs2
 * @param maximum The maximum selection amount (capped at 63)
 */
data class SkillCreation(val items: List<Int>, val type: String, val maximum: Int) : EntityAction(), TaskType<Item> {
    init {
        check(maximum < 64) { "Maximum can't exceed 63"}
    }
    override lateinit var continuation: CancellableContinuation<Item>
}