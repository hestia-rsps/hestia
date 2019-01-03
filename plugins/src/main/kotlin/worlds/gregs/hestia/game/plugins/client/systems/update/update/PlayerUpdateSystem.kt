package worlds.gregs.hestia.game.plugins.client.systems.update.update

import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.update.BasePlayerUpdateSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.PlayerUpdateFlagSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.game.update.UpdateFlag

class PlayerUpdateSystem : BasePlayerUpdateSystem() {

    private lateinit var updateSystem: PlayerUpdateFlagSystem

    override fun flags(): List<UpdateFlag> {
        return updateSystem.updateFlags
    }

    override fun isLocal(type: DisplayFlag?, update: Boolean): Boolean {
        return type != null && type != DisplayFlag.REMOVE
    }

    override fun isGlobal(type: DisplayFlag?, update: Boolean): Boolean {
        return type == DisplayFlag.ADD
    }

}