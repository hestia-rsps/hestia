package worlds.gregs.hestia.content.interaction.npc

import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.NpcOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue.Companion.FIRST
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue.Companion.FOURTH
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue.Companion.SECOND
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue.Companion.THIRD
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.world.movement.model.events.Follow

on(NpcOption, "Talk-to", "Banker") { ->
    fun EntityActions.task(npc: Int) = strongQueue {
        entity perform Follow(npc)
        val within = await(WithinRange(npc, 2))
        entity perform Follow(-1)

        if (!within) {
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }

        dialogue {
            npc perform Watch(entity)
            //TODO unwatch entity if exceeds interact distance * 2
            npc dialogue "Good day. How may I help you?"

            var choice = entity options """
                I'd like to access my bank account, please.
                I'd like to check my PIN settings.
                I'd like to see my collection box.
                What is this place?
            """

            when (choice) {
                FIRST -> TODO("Bank")
                SECOND -> TODO("PIN")
                THIRD -> TODO("Collection")
                FOURTH -> {
                    npc dialogue """
                        This is a branch of the Bank of RuneScape. We have
                        branches in many towns.
                    """

                    choice = entity options """
                        And what do you do?
                        Didn't you used to be called the Bank of Varrock?
                    """

                    when (choice) {
                        FIRST -> npc dialogue """
                            We will look after your items and money for you.
                            Leave your valuables with us if you want to keep them
                            safe.
                        """
                        SECOND -> npc dialogue """
                            Yes we did, but people kept on coming into our
                            branches outside of Varrock and telling us that our
                            signs were wrong. They acted as if we didn't know
                            what town we were in or something.
                        """
                    }
                }
            }
        }
    }
    then(EntityActions::task)
}