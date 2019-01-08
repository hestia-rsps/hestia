package worlds.gregs.hestia.game.plugins.widget.systems.frame.orbs

import worlds.gregs.hestia.game.plugins.widget.components.frame.orbs.HealthOrb
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.ConfigFile
import worlds.gregs.hestia.services.send

class HealthOrbSystem : BaseFrame(HealthOrb::class) {
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        es.send(entityId, ConfigFile(7198, 990))
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 748
        private const val RESIZABLE_INDEX = 177
        private const val FIXED_INDEX = 183
    }
}