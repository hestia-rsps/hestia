package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.plugins.widget.components.screen.FriendsChatSetup
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen
import worlds.gregs.hestia.network.game.out.RunScript
import worlds.gregs.hestia.network.social.out.FriendsChatName
import worlds.gregs.hestia.network.social.out.FriendsChatSetting
import worlds.gregs.hestia.services.send

class FriendsChatSetupSystem : BaseScreen(FriendsChatSetup::class) {

    private var ui: UserInterface? = null

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        //Send details
        GameServer.worldSession?.write(FriendsChatSetting(entityId, ID shl 16, 0))
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            22 -> {//Chat name
                when(option) {
                    1 -> {//Set prefix
                        es.send(entityId, RunScript(109, "Enter chat prefix:"))
                    }
                    2 -> {//Disable
                        GameServer.worldSession?.write(FriendsChatName(entityId, ""))
                    }
                }
            }
            23, 24, 25, 26, 33 -> {
                GameServer.worldSession?.write(FriendsChatSetting(entityId, interfaceHash, option))
            }
            3 -> {//Close
                ui?.close(entityId)
            }
        }
    }

    companion object {
        private const val ID = 1108
    }
}