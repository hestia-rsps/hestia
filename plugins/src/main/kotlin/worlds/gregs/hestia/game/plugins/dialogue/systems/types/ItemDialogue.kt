package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import com.artemis.Entity
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.events.send
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItem

class ItemDialogue(text: String) : BaseEntityDialogue(text) {

    var item: Int = -1

    override fun send(entity: Entity, ui: DialogueBase) {
        //TODO ItemDefinitions - If no title use item name
        super.send(entity, ui)

        val interfaceId = getInterfaceId()

        entity.send(WidgetItem(interfaceId, 2, item, -1))
    }

    override fun getInterfaceId(): Int {
        return ENTITY_ID + lines.size - 1
    }

    override fun getComponentStart(): Int {
        return 3
    }

    override fun getMaxLines(): Int {
        return 4
    }
}

fun Dialogues.item(text: String): ItemDialogue {
    val base = ItemDialogue(text)
    list.add(dialogue.create(base))
    return base
}


infix fun ItemDialogue.id(id: Int): ItemDialogue {
    item = id
    return this
}