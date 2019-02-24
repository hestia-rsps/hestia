package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import com.artemis.Entity
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.events.send
import worlds.gregs.hestia.network.game.encoders.messages.WidgetComponentAnimation
import worlds.gregs.hestia.network.game.encoders.messages.WidgetHeadMob

class MobDialogue(text: String) : BaseEntityDialogue(text) {

    var mob: Int = -1
    var animation: Int = -1

    override fun send(entity: Entity, ui: DialogueBase) {
        //TODO MobDefinitions - If no title use item name
        super.send(entity, ui)

        val interfaceId = getInterfaceId()

        entity.send(WidgetHeadMob(interfaceId, 2, mob))

        if(animation != -1) {
            entity.send(WidgetComponentAnimation(interfaceId, 2, animation))
        }
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

fun Dialogues.mob(text: String): MobDialogue {
    val base = MobDialogue(text)
    list.add(dialogue.create(base))
    return base
}

infix fun MobDialogue.id(id: Int): MobDialogue {
    mob = id
    return this
}

infix fun MobDialogue.anim(id: Int): MobDialogue {
    animation = id
    return this
}