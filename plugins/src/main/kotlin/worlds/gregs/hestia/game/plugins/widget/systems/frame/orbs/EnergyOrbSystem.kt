package worlds.gregs.hestia.game.plugins.widget.systems.frame.orbs

import worlds.gregs.hestia.game.plugins.widget.components.frame.orbs.EnergyOrb
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.RunEnergy
import worlds.gregs.hestia.services.send

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