package worlds.gregs.hestia.content.activity.skill

import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SkillCreation
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SkillCreationAmount
import worlds.gregs.hestia.core.display.interfaces.model.Window.DIALOGUE_BOX
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseWindow
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.display.variable.model.variable.ListVariable
import worlds.gregs.hestia.core.display.variable.model.variable.StringMapVariable
import worlds.gregs.hestia.core.display.variable.model.variable.StringVariable
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceVisibility
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.core.task.api.Tasks

IntVariable(8094, Variable.Type.VARBIT, true, 1).register("skill_creation_maximum")
IntVariable(8095, Variable.Type.VARBIT, true, 1).register("skill_creation_amount")

ListVariable(754, Variable.Type.VARC, values = listOf(
        "Make",
        "Make sets",//No "all"
        "Cook",
        "Roast",
        "Offer",
        "Sell",
        "Bake",
        "Cut",
        "Deposit",
        "Make2",//No "all"
        "Teleport",
        "Select",
        "Make sets2",//No "all"
        "Take",
        "Return",
        "Heat",
        "Add"
)).register("skill_creation_type")

IntVariable(755, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_0")
IntVariable(756, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_1")
IntVariable(757, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_2")
IntVariable(758, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_3")
IntVariable(759, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_4")
IntVariable(760, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_5")
IntVariable(1139, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_6")
IntVariable(1140, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_7")
IntVariable(1141, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_8")
IntVariable(1142, Variable.Type.VARC, defaultValue = -1).register("skill_creation_item_9")

StringVariable(132, Variable.Type.VARCSTR).register("skill_creation_name_0")
StringVariable(133, Variable.Type.VARCSTR).register("skill_creation_name_1")
StringVariable(134, Variable.Type.VARCSTR).register("skill_creation_name_2")
StringVariable(135, Variable.Type.VARCSTR).register("skill_creation_name_3")
StringVariable(136, Variable.Type.VARCSTR).register("skill_creation_name_4")
StringVariable(137, Variable.Type.VARCSTR).register("skill_creation_name_5")
StringVariable(280, Variable.Type.VARCSTR).register("skill_creation_name_6")
StringVariable(281, Variable.Type.VARCSTR).register("skill_creation_name_7")
StringVariable(282, Variable.Type.VARCSTR).register("skill_creation_name_8")
StringVariable(283, Variable.Type.VARCSTR).register("skill_creation_name_9")

lateinit var variables: Variables
lateinit var definitions: ItemDefinitionSystem
lateinit var tasks: Tasks

//Open creation dialogue
on<SkillCreation> {
    then {
        //Open interface
        entity perform OpenInterface(SkillCreation)
        entity perform OpenInterface(SkillCreationAmount)
        //Unlock "all" button
        if (type != "Make sets" && type != "Make2" && type != "Make sets2") {
            entity send InterfaceSettings(SkillCreationAmount, 8, -1, 0, 2)
            entity send InterfaceVisibility(SkillCreation, 6, true)//TODO custom amount entering
        }
        //Send description
        entity send InterfaceComponentText(SkillCreationAmount, 1, "Choose how many you wish to make, then<br>click on the chosen item to begin.")
        //Send right click type
        entity perform SetVariable("skill_creation_type", "Test")

        //Send item options
        repeat(10) { index ->
            val item = items.getOrNull(index) ?: -1
            entity perform SetVariable("skill_creation_item_$index", item)
            if(item != -1) {
                entity perform SetVariable("skill_creation_name_$index", definitions.get(item).name)
            }
        }

        //Set amount max
        entity perform SetVariable("skill_creation_maximum", maximum)
        entity perform SetVariable("skill_creation_amount", maximum)
    }
}

//Amount interface buttons
on<InterfaceInteraction> {
    where { id == SkillCreationAmount }
    then {
        when (component) {
            5 -> entity perform SetVariable("skill_creation_amount", 1, false)
            6 -> entity perform SetVariable("skill_creation_amount", 5, false)
            7 -> entity perform SetVariable("skill_creation_amount", 10, false)
            8 -> entity perform SetVariable("skill_creation_amount", variables.get(entity, "skill_creation_maximum", 1), false)//All
            19 -> {//Increment
                var current = variables.get(entity, "skill_creation_amount", 0)
                val maximum = variables.get(entity, "skill_creation_maximum", 1)
                if(++current > maximum) {
                    current = maximum
                }
                entity perform SetVariable("skill_creation_amount", current)
            }
            20 -> {//Decrement
                var current = variables.get(entity, "skill_creation_amount", 0)
                if(--current < 0) {
                    current = 0
                }
                entity perform SetVariable("skill_creation_amount", current)
            }
        }
    }
}

//When selected close and return result
on<ContinueDialogue> {
    where { id == SkillCreation && option in 14..23 }
    then {
        val choice = option - 14
        val suspension = tasks.getSuspension(entity)
        if(suspension is SkillCreation) {
            val item = suspension.items.getOrNull(choice)
            if(item == null) {
                log("Invalid skill selection $choice $suspension")
                return@then
            }
            val amount = variables.get(entity, "skill_creation_amount", 0)
            tasks.resume(entity, suspension, Item(item, amount))
        } else {
            log("Invalid skill suspension $suspension")
        }
        entity perform CloseWindow(DIALOGUE_BOX)
    }
}