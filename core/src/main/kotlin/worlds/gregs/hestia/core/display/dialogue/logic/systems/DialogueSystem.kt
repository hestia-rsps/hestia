package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.IntegerEntryDialogue
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.model.type.*
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi5Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar5
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.*
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

class DialogueSystem : PassiveSystem() {

    private val logger = LoggerFactory.getLogger(DialogueSystem::class.java)!!
    private lateinit var es: EventSystem
    private lateinit var interfaces: Interfaces
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var mobDefinitions: MobDefinitionSystem
    private lateinit var itemDefinitions: ItemDefinitionSystem
    private lateinit var typeMapper: ComponentMapper<Type>

    private fun openDialogue(entityId: Int, id: Int) {
        interfaces.closeWindow(entityId, Window.DIALOGUE_BOX)
        interfaces.openInterface(entityId, id)
    }

    @Subscribe
    private fun playerChat(action: PlayerChat) {
        val entityId = action.entity
        //Choose window
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) Chat1 else ChatNp1
            2 -> if (action.`continue`) Chat2 else ChatNp2
            3 -> if (action.`continue`) Chat3 else ChatNp3
            4 -> if (action.`continue`) Chat4 else ChatNp4
            else -> return logger.warn("Invalid player chat $action")
        }
        //Open
        openDialogue(entityId, id)

        //Send model
        es.send(entityId, InterfaceHeadPlayer(id, if (action.large) 1 else 2))
        es.send(entityId, InterfaceAnimation(id, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(id, 3, displayNameMapper.get(entityId)?.name ?: ""))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 4 + index, line))
        }
    }

    @Subscribe
    private fun mobChat(action: MobChat) {
        val entityId = action.entity
        val mobType = typeMapper.get(action.mob).id
        //Choose window
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) MobChat1 else MobChatNp1
            2 -> if (action.`continue`) MobChat2 else MobChatNp2
            3 -> if (action.`continue`) MobChat3 else MobChatNp3
            4 -> if (action.`continue`) MobChat4 else MobChatNp4
            else -> return logger.warn("Invalid mob chat $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send model
        es.send(entityId, InterfaceHeadMob(id, if (action.large) 1 else 2, mobType))
        es.send(entityId, InterfaceAnimation(id, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(id, 3, mobDefinitions.get(mobType).name))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 4 + index, line))
        }
    }

    @Subscribe
    private fun statement(action: Statement) {
        val entityId = action.entity
        //Choose interface
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) Message1 else MessageNp1
            2 -> if (action.`continue`) Message2 else MessageNp2
            3 -> if (action.`continue`) Message3 else MessageNp3
            4 -> if (action.`continue`) Message4 else MessageNp4
            5 -> if (action.`continue`) Message5 else MessageNp5
            else -> return logger.warn("Invalid statement $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 1 + index, line))
        }
    }

    @Subscribe
    private fun itemBox(action: ItemBox) {
        val entityId = action.entity
        val id = Interfaces.ObjBox
        openDialogue(entityId, id)

        es.send(entityId, Script(3449, action.model, action.zoom))//TODO test script packet
        es.send(entityId, InterfaceComponentText(id, 1, action.lines))
    }

    @Subscribe
    private fun spriteBox(action: SpriteBox) {
        val entityId = action.entity
        val id = Interfaces.ObjBox
        //Open
        openDialogue(entityId, id)

        //TODO Sprite index - 3
        es.send(entityId, InterfaceComponentText(id, 1, action.lines))
    }

    @Subscribe
    private fun multiOptions(action: MultiOption) {
        val entityId = action.entity
        val multilineTitle = action.title?.contains("<br>") == true
        val multilineOptions = action.options.any { it.contains("<br>") }
        val optionCount = action.options.size
        //Choose interface
        val id = when (optionCount) {
            2 -> if (multilineTitle) MultiVar2 else if (multilineOptions) Multi2Chat else Multi2
            3 -> if (multilineTitle) MultiVar3 else if (multilineOptions) Multi3Chat else Multi3
            4 -> if (multilineTitle) MultiVar4 else if (multilineOptions) Multi4Chat else Multi4
            5 -> if (multilineTitle) MultiVar5 else if (multilineOptions) Multi5Chat else Multi5
            else -> return logger.warn("Invalid multi option $action")
        }
        //Open
        openDialogue(entityId, id)
        val startIndex = if (multilineTitle) 0 else 1
        if (action.title != null) {
            //Update sword locations
            val large = action.title.length > 30//Approximation
            es.send(entityId, InterfaceVisibility(id, if (multilineTitle) 3 + optionCount else 1 + optionCount + 3, large))
            es.send(entityId, InterfaceVisibility(id, if (multilineTitle) 4 + optionCount else 9 + optionCount.rem(2), !large))
            //Send title
            es.send(entityId, InterfaceComponentText(id, startIndex, action.title))
        }
        //Send options
        action.options.forEachIndexed { index, option ->
            es.send(entityId, InterfaceComponentText(id, startIndex + 1 + index, option))
        }
    }

    @Subscribe
    private fun confirmDestroy(action: Destroy) {
        val entityId = action.entity
        val id = Interfaces.ConfirmDestroy
        //Open
        openDialogue(entityId, id)

        es.send(entityId, InterfaceComponentText(id, 7, action.text))
        es.send(entityId, InterfaceComponentText(id, 8, itemDefinitions.get(action.item).name))
        //TODO Sprite index - 9
    }

    @Subscribe
    private fun doubleChat(action: DoubleChat) {
        val entityId = action.entity
        val mobType = typeMapper.get(action.mob).id
        //Choose interface
        val id = when (action.lines.size) {
            1 -> DoubleChat1
            2 -> DoubleChat2
            3 -> DoubleChat3
            4 -> DoubleChat4
            else -> return logger.warn("Invalid double chat $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send left model
        es.send(entityId, InterfaceHeadMob(id, 1, mobType))
        es.send(entityId, InterfaceAnimation(id, 1, action.animation))
        //Send right model
        es.send(entityId, InterfaceHeadPlayer(id, 2))
        es.send(entityId, InterfaceAnimation(id, 2, action.animation))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 3 + index, line))
        }
    }

    private lateinit var tasks: Tasks

    @Subscribe
    private fun closure(event: CloseDialogue) {//TODO replace [CloseDialogue] with CloseWindow(DIALOGUE_BOX)
        interfaces.closeWindow(event.entity, Window.DIALOGUE_BOX)
    }

    @Subscribe
    private fun onContinue(action: ContinueDialogue) {
        when (val suspension = tasks.getSuspension(action.entity)) {
            is MobChat -> tasks.resume(action.entity, suspension, Unit)
            is PlayerChat -> tasks.resume(action.entity, suspension, Unit)
            is Statement -> tasks.resume(action.entity, suspension, Unit)
            is MultiOption -> if(action.option in Dialogue.FIRST..Dialogue.FIFTH) tasks.resume(action.entity, suspension, action.option)
            else -> {
                logger.warn("Unhandled continue $action")
            }
        }
    }
}