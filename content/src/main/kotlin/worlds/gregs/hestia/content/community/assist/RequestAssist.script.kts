package worlds.gregs.hestia.content.community.assist

import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import world.gregs.hestia.core.services.int
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.content.activity.skill.Experience
import worlds.gregs.hestia.content.activity.skill.Skill
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.AreaStatusIcon
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.AssistXP
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FilterButtons
import worlds.gregs.hestia.core.display.window.logic.systems.request
import worlds.gregs.hestia.core.display.window.model.FilterMode
import worlds.gregs.hestia.core.display.window.model.PlayerOptions.ASSIST
import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.display.window.model.components.Assistance
import worlds.gregs.hestia.core.display.window.model.components.Assisting
import worlds.gregs.hestia.core.display.window.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.window.model.events.PlayerOption
import worlds.gregs.hestia.core.display.window.model.events.RequestResponse
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameAssist
import worlds.gregs.hestia.core.task.api.event.target
import worlds.gregs.hestia.core.task.logic.systems.awaitWindow
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.events.Moved
import worlds.gregs.hestia.game.Engine
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.network.client.encoders.messages.WidgetVisibility
import java.util.concurrent.TimeUnit

val skills = listOf(Skill.RUNECRAFTING, Skill.CRAFTING, Skill.FLETCHING, Skill.CONSTRUCTION, Skill.FARMING, Skill.MAGIC, Skill.SMITHING, Skill.COOKING, Skill.HERBLORE)
val config = listOf(4090, 4091, 4093, 4095, 4096, 4098, 4100, 4101, 4102)
val maximumExperience = 30000
val requestDelay = 10

on<PlayerOption> {
    where { option == ASSIST }
    task(TaskPriority.High) {
        val assisting = entity get Assisting::class
        //Delayed requesting
        val lastRequest = Engine.ticks - assisting.lastRequest
        if(lastRequest < requestDelay) {
            val waitTime = requestDelay - lastRequest
            entity type GameAssist message "You have only just made an assistance request"
            entity type GameAssist message "You have to wait $waitTime ${"second".plural(waitTime)} before making a new request."
            return@task
        }
        val targetAssisting = target get Assisting::class
        update(targetAssisting)
        if(targetAssisting.experienceGained >= maximumExperience) {
            entity type GameAssist message "${target.getName()} is unable to assist at the moment."//Unconfirmed
            val hours = assisting.getHoursRemaining()
            target type GameAssist message "An assist request has been refused. You can assist again in $hours ${"hour".plural(hours)}."
            return@task
        }
        entity distance 1 interact target
        request(Request.ASSIST)
        assisting.lastRequest = Engine.ticks
    }
}

//The assistance requester
on<RequestResponse> {
    where { request == Request.ASSIST }
    task {
        entity type GameAssist message "You are being assisted by ${target.getName()}."
        val assistance = entity create Assistance::class
        assistance.helper = target
        assistance.point.set(target get Position::class)
        entity send WidgetVisibility(AreaStatusIcon, 2, false)
    }
}

//The assistance giver
on<AcceptedRequest> {
    where { request == Request.ASSIST }
    task(TaskPriority.High) {
        onCancel { cancel(entity, target) }
        entity type GameAssist message "You are assisting ${target.getName()}."
        val assisting = entity get Assisting::class
        update(assisting)
        entity openWindow AssistXP
        entity send WidgetComponentText(AssistXP, 10, "The Assist System is available for you to use.")
        entity send WidgetComponentText(AssistXP, 73, "Assist System XP Display - You are assisting ${target.getName()}")//TODO there's probably a packet or config for replacing `<name>`
        entity send WidgetVisibility(AreaStatusIcon, 2, false)
        entity send ConfigFile(4103, assisting.experienceGained * 10)
        //TODO disable inventory
        awaitWindow(AssistXP)
        if (entity hasWindowOpen AssistXP) {
            cancel(entity, target)
        }
    }
}

//Handle skill toggling
on<WindowInteraction> {
    where { target == AssistXP && widget in 74..82 }
    task {
        val (_, _, widget) = event(this)
        val assisting = entity create Assisting::class
        val index = widget - 74
        assisting.skills[index] = !assisting.skills[index]
        entity send ConfigFile(config[index], assisting.skills[index].int)
    }
}

//Check if assisted player moves outside of range
on<Moved> {
    whereTask { entity has Assistance::class }
    task {
        val assistance = entity get Assistance::class
        val position = entity get Position::class
        when (entity.movementType()) {
            //Allow teleportation
            MovementType.Move -> assistance.point.set(position)
            else -> {
                //Cancel if player exceeds 20 squares from helper (or teleport point)
                if(!assistance.point.withinDistance(position, 20)) {
                    cancel(assistance.helper, entity)
                }
            }
        }
    }
}

//Filter button handling
on<WindowInteraction> {
    where { target == FilterButtons && widget == 16 }
    task {
        val (_, _, _, _, _, option) = event(this)
        val assisting = entity get Assisting::class
        when(option) {
            1 -> {//View
            }
            2 -> assisting.mode = FilterMode.On
            3 -> assisting.mode = FilterMode.Friends
            4 -> assisting.mode = FilterMode.Off
            9 -> {//Xp Earned/Time
                update(assisting)
                if(assisting.experienceGained >= maximumExperience) {
                    val hours = assisting.getHoursRemaining()
                    entity type GameAssist message "You've earned the maximum XP (30,000 Xp) from the Assist System within a 24-hour period."
                    entity type GameAssist message "You can assist again in $hours ${"hour".plural(hours)}."
                } else {
                    entity type GameAssist message "You have earned ${assisting.experienceGained/10} Xp. The Assist system is available to you."
                }
            }
        }
    }
}

//Intercepting xp
on<Experience>(1) {
    whereTask { entity has Assistance::class }
    task {
        event(this) {
            val assistance = entity get Assistance::class
            val target = assistance.helper
            val assisting = target get Assisting::class
            val index = skills.indexOf(skill)
            //If skill is being assisted
            if(index != -1 && assisting.skills[index] && assisting.experienceGained < maximumExperience) {
                assisting.experienceGained += increase
                if(assisting.experienceGained >= maximumExperience) {
                    target send WidgetComponentText(AssistXP, 10, "You've earned the maximum XP from the Assist System with a 24-hour period.\nYou can assist again in 24 hours.")
                    assisting.timeout = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)
                }
                //TODO what if increase > maximum or entity level > targets
                world dispatch Experience(target, skill, increase)
                target send ConfigFile(4103, assisting.experienceGained * 10)
                isCancelled = true
            }
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
fun Task.update(assisting: Assisting) {
    if(assisting.timeout <= 0) {
        return
    }
    val remainingTime = System.currentTimeMillis() - assisting.timeout
    if(remainingTime <= 0) {
        assisting.experienceGained = 0
        assisting.timeout = 0
        entity type GameAssist message "It has been 24 hours since you first helped someone using the Assist System."
        entity type GameAssist message "You can now use it to gain the full amount of XP."
    }
}

/**
 * Cancels the current assistance
 * Caused by either giver closing/interrupted window or requester moving over 20 tiles away
 * @param entity The helper
 * @param target The assisted
 */
fun Task.cancel(entity: Int, target: Int) {
    entity closeWindow AssistXP
    entity type GameAssist message "You have stopped assisting ${target.getName()}."
    target type GameAssist message "${entity.getName()} has stopped assisting you."//Unconfirmed
    entity send WidgetVisibility(AreaStatusIcon, 2, true)
    target send WidgetVisibility(AreaStatusIcon, 2, true)
    target remove Assistance::class
}