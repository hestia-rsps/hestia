package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Agree
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.dialogue.model.type.MobChat
import worlds.gregs.hestia.core.display.dialogue.model.type.PlayerChat
import worlds.gregs.hestia.core.task.model.await.Ticks


on<Command> {
    where { prefix == "test" }
    fun Command.task() = queue {
        onCancel { entity perform CloseDialogue() }
        entity perform task(2) {
            awaitPerform(entity, MobChat(listOf("one", "two", "three"), 1, Agree))
            awaitPerform(entity, MobChat(listOf("Oh cool"), 1, Agree))
        }
        entity perform task(3) {
            await(Ticks(5))
            awaitPerform(entity, PlayerChat(listOf("stay", "awesome", "gotham"), Agree))
            entity perform CloseDialogue()
        }

//        entity perform ItemBox("Some lines<br>and stuff<br>or not<br>maybe last one", 9009, 650)
//        entity send VarcStr(132, "These are words")
    }
    then(Command::task)
}