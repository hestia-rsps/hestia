package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSetupRequest
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.widget.api.UserInterface
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.FriendsChatTab
import worlds.gregs.hestia.core.display.widget.model.components.screen.FriendsChatSetup
import worlds.gregs.hestia.artemis.events.FriendsChatSetupOpen
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame

class FriendsChatTabSystem : BaseFrame(FriendsChatTab::class) {

    private var ui: UserInterface? = null

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            27 -> {//Join/Leave
            }
            33 -> {//Settings
                GameServer.worldSession?.write(FriendsChatSetupRequest(entityId))
            }
            19 -> {//Toggle loot share
            }
            21 -> {//Kick/Ban
            }
        }
    }

    @Subscribe
    fun send(event: FriendsChatSetupOpen) {
        ui?.open(event.entity, FriendsChatSetup())
    }

    companion object {
        private const val TAB_ID = 1109
        private const val RESIZABLE_INDEX = 100
        private const val FIXED_INDEX = 214
    }
}