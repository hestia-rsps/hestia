package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.events.Disconnect
import worlds.gregs.hestia.core.display.client.model.components.ExitLobby
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.LogoutTab

class LogoutTabSystem : BaseFrame(LogoutTab::class) {

    private lateinit var exitLobbyMapper: ComponentMapper<ExitLobby>

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        when(componentId) {
            6 -> {//Lobby
                if(world.entityManager.isActive(entityId)) {
                    exitLobbyMapper.create(entityId)
                }
                es.dispatch(Disconnect(entityId))
            }
            13 -> {//Login
                es.dispatch(Disconnect(entityId))
            }
        }
    }

    companion object {
        private const val TAB_ID = 182
        private const val RESIZABLE_INDEX = 108
        private const val FIXED_INDEX = 222
    }
}