package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Variables
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.window.model.events.SetVariable
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.variable.StringVariable
import worlds.gregs.hestia.core.entity.entity.model.events.Animate
import worlds.gregs.hestia.core.task.model.await.Forever

StringVariable(173, Variable.Type.VARP, true, "walking", mapOf(
        0 to "walking",
        1 to "running",
        3 to "resting",
        4 to "resting_at_musician"
)).register("energy_orb")

lateinit var variables: Variables

enum class RestType(val start: Int, val end: Int) {
    ARMS_BACK(5713, 5748),
    ARMS_CROSSED(11786, 11788),
    LEGS_STRAIGHT(2716, 2921)
}

on<WindowInteraction> {
    where { target == EnergyOrb && widget == 1 }
    then {
        val current = variables.get(entity, "energy_orb", "walking")
        when(option) {
            //Toggle run
            1 -> {
                //TODO grab value from before resting
                entity perform SetVariable("energy_orb", if(current == "walking") { "running" } else { "walking" })
            }
            2 -> {//Rest
                entity perform task(TaskPriority.High) {
                    val type = RestType.values().random()
                    //TODO store value before rest on blackboard
                    onCancel {
                        //                        entity perform task {
                        entity perform Animate(type.end)
                        entity perform SetVariable("energy_orb", current)
//                        }
                    }
                    entity perform Animate(type.start)
                    entity perform SetVariable("energy_orb", "resting")
                    await(Forever)
                }
            }
        }
    }
}