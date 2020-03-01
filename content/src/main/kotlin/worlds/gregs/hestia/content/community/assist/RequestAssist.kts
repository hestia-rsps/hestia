package worlds.gregs.hestia.content.community.assist

import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.content.activity.skill.Experience
import worlds.gregs.hestia.content.activity.skill.Skill
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.model.ChatType.GameAssist
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.AreaStatusIcon
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.AssistXP
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FilterButtons
import worlds.gregs.hestia.core.display.request.logic.RequestSystem
import worlds.gregs.hestia.core.display.request.model.FilterMode
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions.ASSIST
import worlds.gregs.hestia.core.display.request.model.Request
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseInterface
import worlds.gregs.hestia.core.display.request.model.components.Assistance
import worlds.gregs.hestia.core.display.request.model.components.Assisting
import worlds.gregs.hestia.core.display.request.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.interfaces.model.events.PlayerOption
import worlds.gregs.hestia.core.display.request.model.events.RequestResponse
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.events.ToggleVariable
import worlds.gregs.hestia.core.display.variable.model.variable.BooleanVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement
import worlds.gregs.hestia.core.world.movement.model.events.Follow
import worlds.gregs.hestia.core.world.movement.model.events.Moved
import worlds.gregs.hestia.game.Engine
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceVisibility
import java.util.concurrent.TimeUnit
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.entity.entity.model.events.Animation
import worlds.gregs.hestia.core.entity.entity.model.events.Graphic
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.task.model.await.InterfaceClose
import worlds.gregs.hestia.core.display.interfaces.logic.systems.InterfaceSystem
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.script.on

val skills = listOf(Skill.RUNECRAFTING, Skill.CRAFTING, Skill.FLETCHING, Skill.CONSTRUCTION, Skill.FARMING, Skill.MAGIC, Skill.SMITHING, Skill.COOKING, Skill.HERBLORE)
val maximumExperience = 30000
val requestDelay = 10

BooleanVariable(4091, Variable.Type.VARBIT, true).register("assist_toggle_1")
BooleanVariable(4093, Variable.Type.VARBIT, true).register("assist_toggle_2")
BooleanVariable(4095, Variable.Type.VARBIT, true).register("assist_toggle_3")
BooleanVariable(4096, Variable.Type.VARBIT, true).register("assist_toggle_4")
BooleanVariable(4098, Variable.Type.VARBIT, true).register("assist_toggle_5")
BooleanVariable(4100, Variable.Type.VARBIT, true).register("assist_toggle_6")
BooleanVariable(4101, Variable.Type.VARBIT, true).register("assist_toggle_7")
BooleanVariable(4102, Variable.Type.VARBIT, true).register("assist_toggle_8")

IntVariable(1088, Variable.Type.VARP, true).register("total_xp_earned")//Same as 4103 varbit

lateinit var variables: Variables

on<PlayerOption> {
    where { option == ASSIST }
    fun PlayerOption.task() = strongQueue {
        val assisting = entity get Assisting::class
        //Delayed requesting
        val lastRequest = Engine.ticks - assisting.lastRequest
        if (lastRequest in 1 until requestDelay - 1) {
            val waitTime = requestDelay - lastRequest
            entity perform Chat("You have only just made an assistance request", GameAssist)
            entity perform Chat("You have to wait $waitTime ${"second".plural(waitTime)} before making a new request.", GameAssist)
            return@strongQueue
        }
        val targetAssisting = target get Assisting::class
        update(targetAssisting)
        if (variables.get(target, "total_xp_earned", 0) >= maximumExperience) {
            entity perform Chat("${target.get(DisplayName::class).name} is unable to assist at the moment.", GameAssist)//Unconfirmed
            val hours = assisting.getHoursRemaining()
            target perform Chat("An assist request has been refused. You can assist again in $hours ${"hour".plural(hours)}.", GameAssist)
            return@strongQueue
        }

        entity perform Follow(target)
        val within = await(WithinRange(target, 1))
        entity perform Follow(-1)

        if(!within) {
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }
        system(RequestSystem::class).sendRequest(entity, target, Request.ASSIST)
        assisting.lastRequest = Engine.ticks
    }
    then(PlayerOption::task)
}

//The assistance requester
on<RequestResponse> {
    where { request == Request.ASSIST }
    fun RequestResponse.task() = strongQueue {
        entity perform Chat("You are being assisted by ${target.get(DisplayName::class).name}.", GameAssist)
        val assistance = entity create Assistance::class
        assistance.helper = target
        assistance.point.set(target get Position::class)
        entity send InterfaceVisibility(AreaStatusIcon, 2, false)
        await(Ticks(2))
        entity perform Animation(7299)
    }
    then(RequestResponse::task)
}

//The assistance giver
on<AcceptedRequest> {
    where { request == Request.ASSIST }
    fun AcceptedRequest.task() = strongQueue {
        onCancel { cancel(entity, target) }
        entity perform Chat("You are assisting ${target.get(DisplayName::class).name}.", GameAssist)
        val assisting = entity get Assisting::class
        update(assisting)
        entity perform OpenInterface(AssistXP)
        entity send InterfaceComponentText(AssistXP, 10, "The Assist System is available for you to use.")
        entity send InterfaceComponentText(AssistXP, 73, "Assist System XP Display - You are assisting ${target.get(DisplayName::class).name}")//TODO there's probably a packet or config for replacing `<name>`
        entity send InterfaceVisibility(AreaStatusIcon, 2, false)
        entity perform SendVariable("total_xp_earned")
        entity perform Animation(7299)
        entity perform Graphic(1247)
        //TODO disable inventory
        await(InterfaceClose(AssistXP))
        if (system(InterfaceSystem::class).hasInterface(entity, AssistXP)) {
            cancel(entity, target)
        }
    }
    then(AcceptedRequest::task)
}

//Handle skill toggling
on<InterfaceInteraction> {
    where { id == AssistXP && component in 74..82 }
    then {
        val index = component - 74
        entity perform ToggleVariable("assist_toggle_$index")
    }
}

//Check if assisted player moves outside of range
on<Moved> {
    where { entity has Assistance::class }
    then {
        val assistance = entity get Assistance::class
        val position = entity get Position::class
        when (entity.get(Movement::class).actual) {//Movement type
            //Allow teleportation
            MovementType.Move -> assistance.point.set(position)
            else -> {
                //Cancel if player exceeds 20 squares from helper (or teleport point)
                if (!assistance.point.withinDistance(position, 20)) {
                    cancel(assistance.helper, entity)
                }
            }
        }
    }
}

//Filter button handling
on<InterfaceInteraction> {
    where { id == FilterButtons && component == 16 }
    then {
        val assisting = entity get Assisting::class
        when (option) {
            1 -> {//View
            }
            2 -> assisting.mode = FilterMode.On
            3 -> assisting.mode = FilterMode.Friends
            4 -> assisting.mode = FilterMode.Off
            9 -> {//Xp Earned/Time
                update(assisting)
                val earned = variables.get(entity, "total_xp_earned", 0)
                if (earned >= maximumExperience) {
                    val hours = assisting.getHoursRemaining()
                    entity perform Chat("You've earned the maximum XP (30,000 Xp) from the Assist System within a 24-hour period.", GameAssist)
                    entity perform Chat("You can assist again in $hours ${"hour".plural(hours)}.", GameAssist)
                } else {
                    entity perform Chat("You have earned $earned Xp. The Assist system is available to you.", GameAssist)
                }
            }
        }
    }
}

//Intercepting xp
on<Experience>(1) {
    where { entity has Assistance::class }
    then {
        val assistance = entity get Assistance::class
        val target = assistance.helper
        val assisting = target get Assisting::class
        val index = skills.indexOf(skill)
        val active = variables.get(target, "assist_toggle_$index", false)
        var gained = variables.get(target, "total_xp_earned", 0)
        //If skill is being assisted
        if (index != -1 && active && gained < maximumExperience) {
            gained += increase * 10//TODO decide how to handle experience
            if (gained >= maximumExperience) {
                target send InterfaceComponentText(AssistXP, 10, "You've earned the maximum XP from the Assist System with a 24-hour period.\nYou can assist again in 24 hours.")
                assisting.timeout = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)
            }
            //TODO what if increase > maximum or entity level > targets
            target perform Experience(skill, increase)
            target perform SetVariable("total_xp_earned", gained)
            isCancelled = true
        }
    }
}

/**
 * Returns the number of hours remaining before able to assist again
 */
fun Assisting.getHoursRemaining(): Int {
    val remainingTime = System.currentTimeMillis() - timeout
    return TimeUnit.MILLISECONDS.toHours(remainingTime).toInt()
}

/**
 * Checks to see if the 24 hour timeout has passed
 */
fun EntityAction.update(assisting: Assisting) {
    if (assisting.timeout <= 0) {
        return
    }
    val remainingTime = System.currentTimeMillis() - assisting.timeout
    if (remainingTime <= 0) {
        entity perform SetVariable("total_xp_earned", 0)
        assisting.timeout = 0
        entity perform Chat("It has been 24 hours since you first helped someone using the Assist System.", GameAssist)
        entity perform Chat("You can now use it to gain the full amount of XP.", GameAssist)
    }
}

/**
 * Cancels the current assistance
 * Caused by either giver closing/interrupted interface or requester moving over 20 tiles away
 * @param entity The helper
 * @param target The assisted
 */
fun EntityAction.cancel(entity: Int, target: Int) {
    entity perform CloseInterface(AssistXP)
    entity perform Chat("You have stopped assisting ${target.get(DisplayName::class).name}.", GameAssist)
    target perform Chat("${entity.get(DisplayName::class).name} has stopped assisting you.", GameAssist)//Unconfirmed
    entity send InterfaceVisibility(AreaStatusIcon, 2, true)
    target send InterfaceVisibility(AreaStatusIcon, 2, true)
    target remove Assistance::class
}