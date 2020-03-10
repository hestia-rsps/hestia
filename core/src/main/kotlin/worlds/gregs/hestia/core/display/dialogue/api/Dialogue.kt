package worlds.gregs.hestia.core.display.dialogue.api

import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.display.dialogue.model.DialogueBuilder
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.display.dialogue.model.type.*
import worlds.gregs.hestia.core.task.api.Task

/**
 * A DSL for readable dialogues
 * This is a work around, a proper extensions solution would require [compound extensions](https://github.com/Kotlin/KEEP/pull/176/commits).
 */
class Dialogue(private val action: EntityActions, private val delegate: Task) : Task by delegate {

    private suspend fun player(text: String, animation: Int, large: Boolean, `continue`: Boolean) {
        with(action) {
            entity await delegate perform PlayerChat(animation, large, `continue`, text)
        }
    }

    private suspend fun npc(npc: Int, text: String, animation: Int, large: Boolean, `continue`: Boolean) {
        with(action) {
            entity await delegate perform NpcChat(npc, animation, large, `continue`, text)
        }
    }

    private suspend fun dialogue(entity: Int, text: String, animation: Int = Expression.Talking, large: Boolean = false, `continue`: Boolean = true) {
        with(action) {
            if (this.entity == entity) {
                player(text, animation, large, `continue`)
            } else {
                npc(entity, text, animation, large, `continue`)
            }
        }
    }

    suspend fun options(title: String? = null, text: String) : Int {
        with(action) {
            return entity await delegate perform Options(text, title)
        }
    }

    suspend fun statement(text: String, `continue`: Boolean = true) {
        with(action) {
            entity await delegate perform Statement(text, `continue`)
        }
    }

    suspend fun stringEntry(title: String): String {
        with(action) {
            return entity await delegate perform StringEntry(title)
        }
    }

    suspend fun intEntry(title: String): Int {
        with(action) {
            return entity await delegate perform IntEntry(title)
        }
    }

    suspend fun destroy(text: String, item: Int): Boolean {
        with(action) {
            return entity await delegate perform Destroy(text, item)
        }
    }

    suspend infix fun Int.dialogue(text: String) = dialogue(this, text)

    suspend infix fun DialogueBuilder.dialogue(text: String) = dialogue(target, text, animation, large, `continue`)

    suspend infix fun Int.statement(text: String) = statement(text, true)

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