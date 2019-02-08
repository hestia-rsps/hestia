package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.events.WorldPacket
import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.FriendsChatTab
import worlds.gregs.hestia.game.plugins.widget.components.screen.FriendsChatSetup
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.social.out.FriendsChatSetupRequest

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
    fun send(event: WorldPacket) {
        if(event.packet.opcode == 1) {
            ui?.open(event.entity, FriendsChatSetup())
        }
    }

    companion object {
        private const val TAB_ID = 1109
        private const val RESIZABLE_INDEX = 100
        private const val FIXED_INDEX = 214
    }
}