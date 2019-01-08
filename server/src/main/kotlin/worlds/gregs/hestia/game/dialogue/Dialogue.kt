package worlds.gregs.hestia.game.dialogue

import com.artemis.Entity
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.faces.DialogueLinear

abstract class Dialogue {

    var id: Int = -1
    private var last: Dialogue? = null

    /**
     * Sets the current dialogue as the last
     * @param dialogue The dialogue to add
     * @return dialogue
     */
    fun <T : Dialogue> add(dialogue: T): T {
        last = dialogue
        return dialogue
    }

    /**
     * Connects and adds a sub-dialogue
     * @param dialogue The dialogue to connect
     * @return dialogue
     */
    fun <T : Dialogue> create(dialogue: T): T {
        //Connect previous dialogue to current one
        connect(dialogue)
        //Set current dialogue as the last
        add(dialogue)
        return dialogue
    }

    /**
     * Connects previous dialogue to the current one
     * @param dialogue The next dialogue
     */
    fun connect(dialogue: Dialogue) {
        val previous = last ?: this
        (previous as? DialogueLinear)?.next = dialogue
    }

    abstract fun send(entity: Entity, ui: DialogueBase)

    override fun toString(): String {
        return "[$id] (${this::class.simpleName})"
    }
}

infix fun <T: Dialogue> T.redirectId(id: Int): T {
    this.id = id
    return this
}