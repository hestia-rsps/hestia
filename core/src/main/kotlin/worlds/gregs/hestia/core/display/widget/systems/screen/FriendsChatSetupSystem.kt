package worlds.gregs.hestia.core.display.widget.systems.screen

import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.artemis.events.StringEntered
import worlds.gregs.hestia.core.display.widget.model.components.screen.FriendsChatSetup
import worlds.gregs.hestia.core.display.widget.systems.BaseScreen
import worlds.gregs.hestia.network.client.encoders.messages.Script
import worlds.gregs.hestia.services.send

class FriendsChatSetupSystem : BaseScreen(FriendsChatSetup::class) {

    private var ui: UserInterface? = null

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        //Send details
        GameServer.worldSession?.write(FriendsChatSettings(entityId, ID shl 16, 0))
    }

    @Subscribe
    private fun chatPrefix(event: StringEntered) {
        if(ui?.contains(event.entityId, FriendsChatSetupSystem::class) == true) {
            GameServer.worldSession?.write(FriendsChatName(event.entityId, event.string))
        }
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            22 -> {//Chat name
                when(option) {
                    1 -> {//Set prefix
                        es.send(entityId, Script(109, "Enter chat prefix:"))
                    }
                    2 -> {//Disable
                        GameServer.worldSession?.write(FriendsChatName(entityId, ""))
                    }
                }
            }
            23, 24, 25, 26, 33 -> {
                GameServer.worldSession?.write(FriendsChatSettings(entityId, interfaceHash, option))
            }
//            3 -> {}//Close
        }
    }

    companion object {
        private const val ID = 1108
    }
}