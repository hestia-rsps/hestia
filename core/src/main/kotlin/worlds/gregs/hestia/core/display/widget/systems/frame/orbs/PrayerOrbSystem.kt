package worlds.gregs.hestia.core.display.widget.systems.frame.orbs

import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.PrayerOrb
import worlds.gregs.hestia.core.display.widget.systems.BaseFrame

class PrayerOrbSystem : BaseFrame(PrayerOrb::class) {
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            1 -> {//Orb
                when(option) {
                    1 -> {//Toggle quick prayers
                    }
                    2 -> {//Quick prayers
                    }
                }
            }
        }
    }

    companion object {
        private const val TAB_ID = 749
        private const val RESIZABLE_INDEX = 178
        private const val FIXED_INDEX = 185
    }
}