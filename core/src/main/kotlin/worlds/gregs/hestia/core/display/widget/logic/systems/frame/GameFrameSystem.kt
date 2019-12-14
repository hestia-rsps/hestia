package worlds.gregs.hestia.core.display.widget.logic.systems.frame

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.widget.api.GameFrame
import worlds.gregs.hestia.core.display.widget.api.UserInterface
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFullScreen
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.GameFrameSystem.Companion.FIXED_ID
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.GameFrameSystem.Companion.RESIZABLE_ID
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.LogoutTab
import worlds.gregs.hestia.core.display.widget.model.components.full.WorldMap
import worlds.gregs.hestia.core.script.dsl.task.PlayerOptions
import worlds.gregs.hestia.network.client.encoders.messages.PlayerContextMenuOption
import worlds.gregs.hestia.network.client.encoders.messages.WidgetWindowsPane

@Wire(failOnNull = false, injectInherited = true)
class GameFrameSystem : BaseFullScreen(GameFrame::class) {

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private lateinit var logoutMapper: ComponentMapper<LogoutTab>
    private var ui: UserInterface? = null

    override fun getId(entityId: Int): Int {
        return if(gameFrameMapper.has(entityId)) {
            gameFrameMapper.get(entityId).getId()
        } else {
            -1
        }
    }

    override fun open(entityId: Int) {
        val window = getId(entityId)
        if(window != -1) {
            es.send(entityId, WidgetWindowsPane(window, 0))
            listOf(PlayerOptions.FOLLOW, PlayerOptions.TRADE, PlayerOptions.ASSIST).forEach {
                es.send(entityId, PlayerContextMenuOption(it.string, it.slot, it.top))
            }
        }
    }

    override fun close(entityId: Int) {
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        if(!gameFrameMapper.has(entityId)) {
            return
        }
        val gameFrame = gameFrameMapper.get(entityId)
        when {
            //Logout button
            (gameFrame.resizable && componentId == 176) || (gameFrame.fixed && componentId == 182) -> {
                logoutMapper.create(entityId)
            }
            //Adviser button
            (gameFrame.resizable && componentId == 175) || (gameFrame.fixed && componentId == 184) -> {
            }
            //Compass
            (gameFrame.resizable && componentId == 174) || (gameFrame.fixed && componentId == 178) -> {
            }
            //World Map
            (gameFrame.resizable && componentId == 182) || (gameFrame.fixed && componentId == 180) -> {
                ui?.open(entityId, WorldMap())
            }
            //XP orb
            (gameFrame.resizable && componentId == 229) || (gameFrame.fixed && componentId == 0) -> {
                when(option) {
                    1 -> {}//Toggle
                    7 -> {}//Reset
                }
            }

            gameFrame.resizable && componentId in 39..54 -> {//All tabs
                gameFrame.openTab = componentId - 39
            }
            !gameFrame.resizable && componentId in 129..136 -> {//Top row tabs
                gameFrame.openTab = componentId - 129
            }
            !gameFrame.resizable && componentId in 99..106 -> {//Bottom row tabs
                gameFrame.openTab = componentId - 91//99 - 8
            }
            else -> println("GameFrame click $entityId $componentId $option")
        }
    }

    companion object {
        const val RESIZABLE_ID = 746
        const val FIXED_ID = 548
    }
}

fun GameFrame.getId(): Int {
    return if(resizable) RESIZABLE_ID else FIXED_ID
}