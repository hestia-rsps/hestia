package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import com.artemis.Entity
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.dialogue.faces.DialogueRedirect
import worlds.gregs.hestia.game.events.send
import java.security.InvalidParameterException

class OptionsDialogue : BaseChatDialogue("") {

    var options = ArrayList<OptionDialogue>()

    override fun send(entity: Entity, ui: DialogueBase) {
        val lines = options.asSequence().map { it.text }.toList()

        val interfaceId = when(lines.size) {
            2 -> TWO_OPTIONS_ID
            3 -> THREE_OPTIONS_ID
            4 -> FOUR_OPTIONS_ID
            else -> FIVE_OPTIONS_ID
        }
        val componentStart = if(lines.size == 3) 1 else 0

        ui.openChatInterface(entity.id, interfaceId)

        if(title != null) {
            entity.send(WidgetComponentText(interfaceId, componentStart, title!!))
        }

        if(lines.size > 5) {
            throw InvalidParameterException("Options dialogue must have no more than 5 choices.")
        }

        lines.forEachIndexed { index, line ->
            entity.send(WidgetComponentText(interfaceId, componentStart + index + 1, line))
        }
    }
}

class OptionDialogue(text: String) : BaseChatDialogue(text), DialogueRedirect {
    override val directId: Int
        get() = next?.id ?: -1

    override fun send(entity: Entity, ui: DialogueBase) {}
}


fun Dialogues.option(text: String, action: Dialogues.() -> Unit) {
    dialogue.apply {
        val test = ArrayList<Dialogue>()
        val dialogue = add(OptionDialogue(text))
        list.add(dialogue)
        action(Dialogues(dialogue, test))
    }
}

fun Dialogues.options(title: String? = null, action: Dialogues.() -> Unit) : Dialogue {
    dialogue.apply {
        val list = ArrayList<Dialogue>()
        val dialogue = create(OptionsDialogue())
        dialogue.title = title
        this@options.list.add(dialogue)
        action(Dialogues(dialogue, list))
        dialogue.options.addAll(list.filterIsInstance<OptionDialogue>())
        return dialogue
    }
}