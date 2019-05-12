package worlds.gregs.hestia.content.dialogue

import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueContext
import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueQueue
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.item
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.mob
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.options
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.player

lateinit var dialogueBase: DialogueBase

on<Command> {
    where { it.prefix == "test" }
    then {
        dialogueBase.startDialogue(it.entityId, "Man")
    }
}

dialogue("Man") {
    mob("This is a mob statement", 0, 9878)
    player("Hi")
    mob("Hello", 0, title = "Not a noob")
    mob("I like to think that sometimes I'm lots of lines", 0, title = "Noob")
    choice()

    options("What to do?", aThing, "What can I do here?", aThing)
}

val aThing: DialogueQueue = {
    mob("You can have fun!")
}

suspend fun QueueScope<DialogueContext>.choice() {
    when (options("First choice", "Second choice", "Third choice", "Fourth choice", "Fifth choice")) {
        1 -> {
            mob("First", 100)
            player("Very well then")
            println("Give item")
            item("The mob gives you an item", 11694)
            return
        }
        2 -> {
            mob("Second", 100)
            println("Running!")
            mob("Sixth!", 100)
        }
        3 -> mob("Third", 100)
        4 -> mob("Fourth", 100)
        5 -> mob("Fifth", 100)
    }
    choice()
}