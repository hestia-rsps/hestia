package worlds.gregs.hestia.game.plugins.client.systems.update.update

import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.update.BaseMobUpdateSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.MobUpdateFlagSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.game.update.UpdateFlag

class MobUpdateSystem : BaseMobUpdateSystem() {

    private lateinit var updateSystem: MobUpdateFlagSystem

    override fun flags(): List<UpdateFlag> {
        return updateSystem.updateFlags
    }

    override fun isLocal(type: DisplayFlag?, update: Boolean): Boolean {
        return update
    }

    override fun isGlobal(type: DisplayFlag?, update: Boolean): Boolean {
        return update
    }
}