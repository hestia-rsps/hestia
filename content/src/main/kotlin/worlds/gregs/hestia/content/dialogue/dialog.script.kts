package worlds.gregs.hestia.content.dialogue

import worlds.gregs.hestia.core.display.dialogue.api.DialogueBase
import worlds.gregs.hestia.core.display.dialogue.systems.types.item
import worlds.gregs.hestia.core.display.dialogue.systems.types.mob
import worlds.gregs.hestia.core.display.dialogue.systems.types.options
import worlds.gregs.hestia.core.display.dialogue.systems.types.player
import worlds.gregs.hestia.game.task.DeferQueue
import worlds.gregs.hestia.game.task.TaskPriority
import worlds.gregs.hestia.game.task.TaskScope

lateinit var dialogueBase: DialogueBase

on<Command> {
    where { it.prefix == "test" }
    then {
        dialogueBase.startDialogue(it.command.split(" ")[1].toInt(), "Man")
    }
}

dialogue("Man", TaskPriority.Weak) {
//    val entered = stringEntry("What's your name?")
//    println(entered)
    mob("This is a mob statement", 0, 9878)
//    wait(4)
//    val entered = stringEntry("What's your name?")
//    mob("Hello $entered", 0, title = "Not a noob")
//    player("Hi")
//    mob("I like to think that sometimes I'm lots of lines", 0, title = "Noob")
//    choice()

    options("What to do?", aThing, "What can I do here?", aThing)
}


val aThing: DeferQueue = {
    mob("You can have fun!")
}

suspend fun TaskScope.choice() {
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