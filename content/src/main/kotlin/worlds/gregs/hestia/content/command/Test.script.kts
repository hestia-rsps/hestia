package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.content.activity.skill.Experience
import worlds.gregs.hestia.content.activity.skill.Skill.CRAFTING
import worlds.gregs.hestia.core.action.Action
import worlds.gregs.hestia.core.display.client.model.events.Command

data class TestAction(val someData: Int = 0) : Action() {
    constructor(entity: Int, someData: Int) : this(someData) {
        this.entity = entity
    }
}

on<TestAction> {
    where { someData == 1 || entity == 4 }

    then {
        entity perform TestAction(2)
        entity perform task {
            entity perform TestAction(2)
        }
        someData
    }
//    fun TestAction.task() = queue {
//        entity perform TestAction(2)
//    }
//    then(TestAction::task)
}

on<Command> {
    where { prefix == "test" }

    fun Command.task() = queue {
        onCancel { closeDialogue() }
        val es = world system EventSystem::class
//        entity openWindow AssistXP
        world dispatch Experience(CRAFTING, 10000)
//        entity send WidgetComponentText(AssistXP, 10, "You've earned the maximum XP from the Assist System with a 24-hour period.<br>You can assist again in 24 hours.")
//        entity send Script(527)
//        entity send Script(524, 301 shl 16 or 45)
//        entity send ConfigGlobal(4092, 1)
//        option("This is a really long title which is more than normal.", listOf("one", "two", "three", "four", "five"))
//        option("This is a really long title which is more than normal.", listOf("one", "two", "three", "four"))
//        option("This is a really long title which is more than normal.", listOf("one", "two", "three"))
//        option("This is a really long title which is more than normal.", listOf("one", "two"))
//        player("Hey", 9760)
//        mob("Hello", 1, content.toInt(), "Hans")
//        closeDialogue()
    }
    then(Command::task)
}