package worlds.gregs.hestia.content.interaction.mob

import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.entity.mob.model.events.MobOption
import worlds.gregs.hestia.core.task.api.Task.Companion.FIRST
import worlds.gregs.hestia.core.task.api.Task.Companion.FOURTH
import worlds.gregs.hestia.core.task.api.Task.Companion.SECOND
import worlds.gregs.hestia.core.task.api.Task.Companion.THIRD

on<MobOption> {
    where { option == "Talk-to" && name == "Banker" }
    fun MobOption.task() = queue(TaskPriority.High) {
        entity.interact(target, 2)
        onCancel { entity perform CloseDialogue() }

        target.create(Watch::class).entity = entity
        //TODO unwatch entity if exceeds interact distance * 2
        target dialogue "Good day. How may I help you?"

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
                target dialogue """
                        This is a branch of the Bank of RuneScape. We have
                        branches in many towns.
                    """

                choice = entity options """
                        And what do you do?
                        Didn't you used to be called the Bank of Varrock?
                    """

                when (choice) {
                    FIRST -> target dialogue """
                            We will look after your items and money for you.
                            Leave your valuables with us if you want to keep them
                            safe.
                        """
                    SECOND -> target dialogue """
                            Yes we did, but people kept on coming into our
                            branches outside of Varrock and telling us that our
                            signs were wrong. They acted as if we didn't know
                            what town we were in or something.
                        """
                }
            }
        }
        entity perform CloseDialogue()
    }
    then(MobOption::task)
}