package worlds.gregs.hestia.game.plugins.widget.systems.frame.orbs

import worlds.gregs.hestia.game.plugins.widget.components.frame.orbs.SummoningOrb
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.services.send

class SummoningOrbSystem : BaseFrame(SummoningOrb::class) {
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        es.send(entityId, Config(1160, -1))
        super.open(entityId)
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 747
        private const val RESIZABLE_INDEX = 180
        private const val FIXED_INDEX = 188
    }
}