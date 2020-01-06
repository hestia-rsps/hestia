package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.perform
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.events.WindowSwitch
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WindowSwitchWidgets

class WindowSwitchWidgetsHandler : MessageHandlerSystem<WindowSwitchWidgets>() {

    private val logger = LoggerFactory.getLogger(WindowSwitchWidgetsHandler::class.java)!!
    private lateinit var windows: Windows
    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WindowSwitchWidgets) {
        val (fromType, fromIndex, toType, fromHash, toIndex, toHash) = message
        if(windows.verifyWindow(entityId, fromHash) && windows.verifyWindow(entityId, toHash)) {
            es.perform(entityId, WindowSwitch( fromHash shr 16, fromIndex, fromType, toHash shr 16, toIndex, toType))
        } else {
            logger.warn("Invalid widget component switch hash $message")
        }
    }
}