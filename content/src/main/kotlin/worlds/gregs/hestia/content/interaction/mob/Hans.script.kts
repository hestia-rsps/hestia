package worlds.gregs.hestia.content.interaction.mob

import arrow.syntax.function.andThen
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Agree
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Delayed
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Disregard
import worlds.gregs.hestia.core.display.dialogue.model.Expression.EvilLaugh
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Laugh
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Sad
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Uncertain
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.update.model.components.ForceChat
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.entity.mob.model.events.MobOption
import worlds.gregs.hestia.core.entity.player.model.components.Member
import worlds.gregs.hestia.core.task.api.Task.Companion.FIRST
import worlds.gregs.hestia.core.task.api.Task.Companion.FOURTH
import worlds.gregs.hestia.core.task.api.Task.Companion.SECOND
import worlds.gregs.hestia.core.task.api.Task.Companion.THIRD
import worlds.gregs.hestia.core.task.logic.systems.WithinRange
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.events.Follow

val year = 365
val fiveYears = year * 5

val veteranCape = remove(995, 50000) andThen addAll(20763, 20764)

on<MobOption> {
    where { option == "Talk-to" && name == "Hans" }
    fun MobOption.task() = queue(TaskPriority.High) {

        entity perform Follow(target)
        val within = await(WithinRange(target, 1))
        entity perform Follow(-1)

        if(!within) {
            entity perform Chat("You can't reach that.")
            return@queue
        }

        onCancel { entity perform CloseDialogue() }

        target perform Watch(entity)
        target dialogue "Hello. What are you doing here?"

        var choice = entity options """
            I'm looking for whoever is in charge of this place.
            I have come to kill everyone in this castle!
            I don't know. I'm lost. Where am I?
            ${if (entity has Member::class) "What do you do here?" else ""}
        """

        when (choice) {
            FIRST -> {
                entity dialogue "I'm looking for whoever is in charge of this place."
                target dialogue "Who, the Duke? He's in his study, on the first floor."
            }
            SECOND -> {
                entity animation EvilLaugh dialogue "I have come to kill everyone in this castle!"
                //TODO("target stepAway entity")
                target.create(ForceChat::class).message = "Help! Help!"
            }
            THIRD -> {
                entity animation Uncertain dialogue "I don't know. I'm lost. Where am I?"
                target dialogue "You are in Lumbridge Castle."
            }
            FOURTH -> {
                entity animation Uncertain dialogue "What do you do here?"
                target animation Delayed dialogue "I'm on patrol. I've been patrolling this castle for years!"
                entity animation Uncertain dialogue "You must be old then?"
                target animation Laugh dialogue """
                    Haha, you could say I'm quite the veteran of these
                    lands. Yes, I've been here a fair while...
                """

                var playTimeDays = fiveYears + 1//TODO Account creation date
                val years = playTimeDays/year

                if (years < 5) {
                    target animation Sad dialogue "Sadly you are not eligible for the ten-year veteran cape."
                    val daysLeft = fiveYears - playTimeDays
                    target dialogue when (daysLeft) {
                        in 2..364 -> "Not long until you are now too, only $daysLeft days."
                        1 -> "Not long until you are now too, only 1 day!"
                        else -> "It'll be longer than a year for you yet I'm afraid."
                    }
                    entity perform Chat("You will be able to claim the 5-year veteran cape from Hans ${if (daysLeft > 1) "in $daysLeft ${"day".plural(daysLeft)}." else "tomorrow!"}")
                } else {
                    target animation Disregard dialogue """
                        And it looks like you've been here for a decent amount
                        of time too! I can sell you a cape to show you are a
                        veteran too, if you'd like - only 50,000 coins!
                    """
                    choice = entity title "Pay 50,000 coins for a veteran cape?" options """
                        I'll take one!
                        No, thanks.
                    """
                    if(choice == FIRST) {
                        entity animation Agree dialogue "I'll take one!"
                        if(entity purchase veteranCape) {
                            target animation Laugh dialogue "Thanks. Enjoy!"
                        }
                    }
                }
            }
        }
        entity perform CloseDialogue()
    }
    then(MobOption::task)
}