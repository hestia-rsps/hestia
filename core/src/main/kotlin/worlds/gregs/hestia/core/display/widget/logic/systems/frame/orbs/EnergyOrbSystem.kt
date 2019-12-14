package worlds.gregs.hestia.core.display.widget.logic.systems.frame.orbs

import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.EnergyOrb
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.RunEnergy
import worlds.gregs.hestia.artemis.send

class EnergyOrbSystem : BaseFrame(EnergyOrb::class) {
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        es.send(entityId, RunEnergy(100))
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            1 -> {//Orb
                when(option) {
                    1 -> {//Toggle run mode
                    }
                    2 -> {//Rest
                    }
                }
            }
        }
    }

    companion object {
        private const val TAB_ID = 750
        private const val RESIZABLE_INDEX = 179
        private const val FIXED_INDEX = 186
    }
}