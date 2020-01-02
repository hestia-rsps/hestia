package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.display.client.model.Configs.COMBAT_STYLE
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.CombatStylesTab
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings
import worlds.gregs.hestia.artemis.send

@Wire(injectInherited = true)
class CombatStylesTabSystem : BaseFrame(CombatStylesTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        unlock(entityId)
    }

    fun lock(entityId: Int) {
        for (componentId in 11..14) {
            es.send(entityId, WidgetComponentSettings(getId(entityId), componentId, -1, 0))
        }
    }

    fun unlock(entityId: Int) {
        for (componentId in 11..14) {
            es.send(entityId, WidgetComponentSettings(getId(entityId), componentId, -1, 0, options = *intArrayOf(0)))
        }
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        when(componentId) {
            4 -> {}//Special attack bar
            in 11 .. 14 -> {//Attack style
                val attackStyle = componentId - 11
                es.send(entityId, Config(COMBAT_STYLE, attackStyle))
            }
            15 -> {}//Auto retaliate
        }
    }

    companion object {
        private const val TAB_ID = 884
        private const val RESIZABLE_INDEX = 90
        private const val FIXED_INDEX = 204
    }
}