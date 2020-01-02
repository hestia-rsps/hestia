package worlds.gregs.hestia.core.display.widget.logic.systems.frame.orbs

import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.HealthOrb
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.artemis.send

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

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 748
        private const val RESIZABLE_INDEX = 177
        private const val FIXED_INDEX = 183
    }
}