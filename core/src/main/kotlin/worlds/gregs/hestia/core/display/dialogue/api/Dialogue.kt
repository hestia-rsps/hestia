package worlds.gregs.hestia.core.display.dialogue.api

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.dialogue.model.DialogueBuilder
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.display.dialogue.model.type.MobChat
import worlds.gregs.hestia.core.display.dialogue.model.type.MultiOption
import worlds.gregs.hestia.core.display.dialogue.model.type.PlayerChat
import worlds.gregs.hestia.core.display.dialogue.model.type.Statement
import worlds.gregs.hestia.core.task.api.Task

/**
 * A DSL for readable dialogues
 */
class Dialogue(private val action: EntityAction, private val delegate: Task) : Task by delegate {

    private suspend fun player(text: String, animation: Int, large: Boolean, `continue`: Boolean) {
        with(action) {
            entity await delegate perform PlayerChat(animation, large, `continue`, text)
        }
    }

    private suspend fun mob(mob: Int, text: String, animation: Int, large: Boolean, `continue`: Boolean) {
        with(action) {
            entity await delegate perform MobChat(mob, animation, large, `continue`, text)
        }
    }

    private suspend fun dialogue(entity: Int, text: String, animation: Int = Expression.Talking, large: Boolean = false, `continue`: Boolean = true) {
        with(action) {
            if (this.entity == entity) {
                player(text, animation, large, `continue`)
            } else {
                mob(entity, text, animation, large, `continue`)
            }
        }
    }

    suspend fun options(title: String? = null, text: String) : Int {
        with(action) {
            return entity await delegate perform MultiOption(text, title)
        }
    }

    suspend fun statement(text: String, `continue`: Boolean = true) {
        with(action) {
            entity await delegate perform Statement(text, `continue`)
        }
    }

    suspend infix fun Int.dialogue(text: String) = dialogue(this, text)

    suspend infix fun DialogueBuilder.dialogue(text: String) = dialogue(target, text, animation, large, `continue`)

    suspend infix fun DialogueBuilder.statement(text: String) = statement(text, `continue`)

    suspend infix fun Int.options(text: String) = options(null, text)

    suspend infix fun DialogueBuilder.options(text: String) = options(title, text)


    infix fun DialogueBuilder.animation(animation: Int) = apply { this.animation = animation }

    infix fun Int.animation(animation: Int) = DialogueBuilder(this, animation = animation)

    infix fun DialogueBuilder.title(title: String) = apply { this.title = title }

    infix fun Int.title(title: String) = DialogueBuilder(this, title = title)

    infix fun Int.cont(`continue`: Boolean) = DialogueBuilder(this, `continue` = `continue`)

    infix fun DialogueBuilder.cont(`continue`: Boolean) = apply { this.`continue` = `continue` }

    infix fun Int.large(large: Boolean) = DialogueBuilder(this, large = large)

    infix fun DialogueBuilder.large(large: Boolean) = apply { this.large = large }

    companion object {
        const val FIRST = 2
        const val SECOND = 3
        const val THIRD = 4
        const val FOURTH = 5
        const val FIFTH = 6
        val SUCCESS = null
    }
}