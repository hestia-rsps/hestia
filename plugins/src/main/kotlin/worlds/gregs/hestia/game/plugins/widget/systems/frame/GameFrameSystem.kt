package worlds.gregs.hestia.game.plugins.widget.systems.frame

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.api.widget.Widget
import worlds.gregs.hestia.game.plugins.widget.components.full.WorldMap
import worlds.gregs.hestia.game.plugins.widget.components.tabs.LogoutTab
import worlds.gregs.hestia.game.plugins.widget.systems.frame.GameFrameSystem.Companion.FIXED_ID
import worlds.gregs.hestia.game.plugins.widget.systems.frame.GameFrameSystem.Companion.RESIZABLE_ID
import worlds.gregs.hestia.network.out.WindowsPane
import worlds.gregs.hestia.services.send

@Wire(failOnNull = false)
class GameFrameSystem : Widget(GameFrame::class) {

    private lateinit var es: EventSystem
    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private lateinit var logoutMapper: ComponentMapper<LogoutTab>
    private var ui: UserInterface? = null
    override var id = -1

    override fun getId(entityId: Int): Int {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            return if(gameFrame.resizable) RESIZABLE_ID else FIXED_ID
        }
        return -1
    }

    override fun inserted(entityId: Int) {
        open(entityId)
    }

    override fun getIndex(resizable: Boolean): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun open(entityId: Int) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            es.send(entityId, WindowsPane(gameFrame.getId(), 0))
        }
    }

    override fun close(entityId: Int) {
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
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