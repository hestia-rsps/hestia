package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.model.type.*
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi2Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi3Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi4Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi5Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar5
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.network.client.encoders.messages.*
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

class DialogueSystem : PassiveSystem() {

    private val logger = LoggerFactory.getLogger(DialogueSystem::class.java)!!
    private lateinit var es: EventSystem
    private lateinit var windows: Windows
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var mobDefinitions: MobDefinitionSystem
    private lateinit var itemDefinitions: ItemDefinitionSystem

    @Subscribe
    private fun playerChat(action: PlayerChat) {
        val entityId = action.entity
        //Choose window
        val window = when (action.lines.size) {
            1 -> if (action.`continue`) Chat1 else ChatNp1
            2 -> if (action.`continue`) Chat2 else ChatNp2
            3 -> if (action.`continue`) Chat3 else ChatNp3
            4 -> if (action.`continue`) Chat4 else ChatNp4
            else -> return logger.warn("Invalid player chat $action")
        }
        //Open
        windows.closeWindows(entityId, WindowPane.DIALOGUE_BOX)
        windows.openWindow(entityId, window)

        //Send model
        es.send(entityId, InterfaceHeadPlayer(window, if (action.large) 1 else 2))
        es.send(entityId, InterfaceAnimation(window, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(window, 3, displayNameMapper.get(entityId)?.name ?: ""))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(window, 4 + index, line))
        }
    }

    @Subscribe
    private fun mobChat(action: MobChat) {
        val entityId = action.entity
        //Choose window
        val window = when (action.lines.size) {
            1 -> if (action.`continue`) MobChat1 else MobChatNp1
            2 -> if (action.`continue`) MobChat2 else MobChatNp2
            3 -> if (action.`continue`) MobChat3 else MobChatNp3
            4 -> if (action.`continue`) MobChat4 else MobChatNp4
            else -> return logger.warn("Invalid mob chat $action")
        }
        //Open
        windows.closeWindows(entityId, WindowPane.DIALOGUE_BOX)
        windows.openWindow(entityId, window)
        //Send model
        es.send(entityId, InterfaceHeadMob(window, if (action.large) 1 else 2, action.mob))
        es.send(entityId, InterfaceAnimation(window, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(window, 3, mobDefinitions.get(action.mob).name))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(window, 4 + index, line))
        }
    }

    @Subscribe
    private fun statement(action: Statement) {
        val entityId = action.entity
        //Choose window
        val window = when (action.lines.size) {
            1 -> if (action.`continue`) Message1 else MessageNp1
            2 -> if (action.`continue`) Message2 else MessageNp2
            3 -> if (action.`continue`) Message3 else MessageNp3
            4 -> if (action.`continue`) Message4 else MessageNp4
            5 -> if (action.`continue`) Message5 else MessageNp5
            else -> return logger.warn("Invalid statement $action")
        }
        //Open
        windows.openWindow(entityId, window)
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(window, 1 + index, line))
        }
    }

    @Subscribe
    private fun itemBox(action: ItemBox) {
        val entityId = action.entity
        val window = Windows.ObjBox
        windows.openWindow(entityId, window)

        es.send(entityId, Script(3449, action.model, action.zoom))//TODO test script packet
        es.send(entityId, InterfaceComponentText(window, 1, action.lines))
    }

    @Subscribe
    private fun spriteBox(action: SpriteBox) {
        val entityId = action.entity
        val window = Windows.ObjBox
        //Open
        windows.openWindow(entityId, window)

        //TODO WidgetSprite widget index - 3
        es.send(entityId, InterfaceComponentText(window, 1, action.lines))
    }

    @Subscribe
    private fun multiOptions(action: MultiOption) {
        val entityId = action.entity
        val multilineTitle = action.title?.contains("<br>") == true
        val multilineOptions = action.options.any { it.contains("<br>") }
        val optionCount = action.options.size
        //Choose window
        val window = when (optionCount) {
            2 -> if (multilineTitle) MultiVar2 else if (multilineOptions) Multi2Chat else Multi2
            3 -> if (multilineTitle) MultiVar3 else if (multilineOptions) Multi3Chat else Multi3
            4 -> if (multilineTitle) MultiVar4 else if (multilineOptions) Multi4Chat else Multi4
            5 -> if (multilineTitle) MultiVar5 else if (multilineOptions) Multi5Chat else Multi5
            else -> return logger.warn("Invalid multi option $action")
        }
        //Open
        windows.openWindow(entityId, window)
        val startIndex = if (multilineTitle) 0 else 1
        if (action.title != null) {
            //Update sword locations
            val large = action.title.length > 30//Approximation
            es.send(entityId, InterfaceVisibility(window, if (multilineTitle) 3 + optionCount else 1 + optionCount + 3, large))
            es.send(entityId, InterfaceVisibility(window, if (multilineTitle) 4 + optionCount else 9 + optionCount.rem(2), !large))
            //Send title
            es.send(entityId, InterfaceComponentText(window, startIndex, action.title))
        }
        //Send options
        action.options.forEachIndexed { index, option ->
            es.send(entityId, InterfaceComponentText(window, startIndex + 1 + index, option))
        }
    }

    @Subscribe
    private fun confirmDestroy(action: Destroy) {
        val entityId = action.entity
        val window = Windows.ConfirmDestroy
        windows.openWindow(entityId, window)

        es.send(entityId, InterfaceComponentText(window, 7, action.text))
        es.send(entityId, InterfaceComponentText(window, 8, itemDefinitions.get(action.item).name))
        //TODO WidgetSprite widget index - 9
    }

    @Subscribe
    private fun doubleChat(action: DoubleChat) {
        val entityId = action.entity
        //Choose window
        val window = when (action.lines.size) {
            1 -> DoubleChat1
            2 -> DoubleChat2
            3 -> DoubleChat3
            4 -> DoubleChat4
            else -> return logger.warn("Invalid double chat $action")
        }
        //Open
        windows.openWindow(entityId, window)
        //Send left model
        es.send(entityId, InterfaceHeadMob(window, 1, action.mob))
        es.send(entityId, InterfaceAnimation(window, 1, action.animation))
        //Send right model
        es.send(entityId, InterfaceHeadPlayer(window, 2))
        es.send(entityId, InterfaceAnimation(window, 2, action.animation))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(window, 3 + index, line))
        }
    }

    private lateinit var tasks: Tasks

    @Subscribe
    private fun closure(event: CloseDialogue) {//TODO replace [CloseDialogue] with CloseWindow(DIALOGUE_BOX)
        windows.closeWindows(event.entity, WindowPane.DIALOGUE_BOX)
    }

    @Subscribe
    private fun onContinue(action: ContinueDialogue) {
        when(val suspension = tasks.getSuspension(action.entity)) {
            is MobChat -> tasks.resume(action.entity, suspension, Unit)
            is PlayerChat -> {
                tasks.resume(action.entity, suspension, Unit)
            }
        }
    }
}