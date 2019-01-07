package worlds.gregs.hestia.game.plugins.widget.systems.tabs

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.Configs.COMBAT_STYLE
import worlds.gregs.hestia.game.plugins.widget.components.tabs.CombatStylesTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseTab
import worlds.gregs.hestia.network.out.Config
import worlds.gregs.hestia.network.out.UnlockComponentOption
import worlds.gregs.hestia.services.send

@Wire(injectInherited = true)
class CombatStylesTabSystem : BaseTab(CombatStylesTab::class) {

    override var id = TAB_ID

    override fun open(entityId: Int) {
        super.open(entityId)
        unlock(entityId)
    }

    fun lock(entityId: Int) {
        for (componentId in 11..14) {
            es.send(entityId, UnlockComponentOption(id, componentId, -1, 0))
        }
    }

    fun unlock(entityId: Int) {
        for (componentId in 11..14) {
            es.send(entityId, UnlockComponentOption(id, componentId, -1, 0, 0))
        }
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
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