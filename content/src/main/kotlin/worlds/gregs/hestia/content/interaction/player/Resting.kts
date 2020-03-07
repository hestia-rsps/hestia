package worlds.gregs.hestia.content.interaction.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.action.model.*
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.update.model.components.UpdateMovement
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.variable.StringMapVariable
import worlds.gregs.hestia.core.entity.entity.model.components.Blackboard
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.events.Animation
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.model.await.Forever
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.world.movement.model.components.RunToggled
import worlds.gregs.hestia.core.world.movement.model.events.Follow

StringMapVariable(173, Variable.Type.VARP, true, mapOf(
        "walking" to 0,
        "running" to 1,
        "resting" to 3,
        "resting_at_musician" to 4
)).register("energy_orb")

lateinit var variables: Variables
lateinit var runToggledMapper: ComponentMapper<RunToggled>
lateinit var updateMovementMapper: ComponentMapper<UpdateMovement>

enum class RestType(val sit: Int, val stand: Int) {
    ARMS_BACK(5713, 5748),
    ARMS_CROSSED(11786, 11788),
    LEGS_STRAIGHT(2716, 2921)
}

worlds.gregs.hestia.core.action.logic.systems.on(NpcOption, "Listen-to", "Musician") { ->
    fun EntityActions.task(npc: Int) = strongQueue {
        entity perform Follow(npc)
        val within = await(WithinRange(npc, 1))
        entity perform Follow(-1)
        entity perform Face(npc get Position::class)

        if (!within) {
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }
        val blackboard = entity get Blackboard::class
        val current = variables.get(entity, "energy_orb", "walking")
        blackboard["unrest_state"] = current
        entity perform rest(true)
    }
    then(EntityActions::task)
}

on(InterfaceOption, "Turn Run mode on", id = EnergyOrb) { _, _, _, _ ->
    val blackboard = entity get Blackboard::class
    val current = variables.get(entity, "energy_orb", "walking")
    if (current == "walking" || current == "running") {
        val run = current == "walking"
        runToggledMapper.set(entity, run)
        updateMovementMapper.create(entity)
        entity perform SetVariable("energy_orb", if (run) {
            "running"
        } else {
            "walking"
        })
    } else {
        blackboard["unrest_state"] = "running"
        runToggledMapper.set(entity, true)
        updateMovementMapper.create(entity)
        entity perform SendVariable("energy_orb")
    }
}

on(InterfaceOption, "Rest", id = EnergyOrb) { _, _, _, _ ->
    val blackboard = entity get Blackboard::class
    val current = variables.get(entity, "energy_orb", "walking")
    blackboard["unrest_state"] = current
    entity perform rest(false)
}

fun EntityActions.rest(musician: Boolean) = strongTask {
    val blackboard = entity get Blackboard::class
    //If cancelled then stand up
    onCancel {
        //If next task movement type is walking
        val slowly = blackboard.getString("unrest_state") == "walking" || if (it is TaskCancellation.Walk) entity.get(Position::class).getDistance(Position(it.x, it.y)) <= 1 else false
        entity perform standUp(slowly)
    }
    //Choose a rest animation
    val type = RestType.values().random()
    //Save stand-up animation for later
    blackboard["stand_up_anim"] = type.stand
    //Sit down animation
    entity perform Animation(type.sit)
    //Update orb
    entity perform SetVariable("energy_orb", if (musician) "resting_at_musician" else "resting")
    await(Forever)
}

fun EntityActions.standUp(slowly: Boolean) = task(2) {
    val blackboard = entity get Blackboard::class
    //Perform stand up animation
    val animation = blackboard.getInt("stand_up_anim")
    entity await this perform Animation(animation)
    //Cancel animation quickly or slowly
    if (slowly) {
        await(Ticks(1))
    }
    entity perform Animation(-1)
    //Send orb update
    entity perform SetVariable("energy_orb", blackboard.getString("unrest_state"))
    //Clean-up
    blackboard.remove("stand_up_anim")
    blackboard.remove("unrest_state")
}