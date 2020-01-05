package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.content.activity.skill.Experience
import worlds.gregs.hestia.content.activity.skill.Skill.CRAFTING
import worlds.gregs.hestia.core.display.client.model.events.Command

on<Command> {
    where { prefix == "test" }
    task {
        onCancel { closeDialogue() }
        val (_, _, content) = event(this)
        val es = world system EventSystem::class
//        entity openWindow AssistXP
        world dispatch Experience(entity, CRAFTING, 10000)
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
}