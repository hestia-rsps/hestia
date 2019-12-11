package worlds.gregs.hestia.core.plugins.widget.systems.screen

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.plugins.widget.components.screen.CustomScreenWidget
import worlds.gregs.hestia.core.plugins.widget.systems.BaseScreen

class CustomScreenWidgetSystem : BaseScreen(CustomScreenWidget::class) {

    private lateinit var customScreenWidgetMapper: ComponentMapper<CustomScreenWidget>

    override fun getId(entityId: Int): Int {
        return if(customScreenWidgetMapper.has(entityId)) {
            val customScreenWidget = customScreenWidgetMapper.get(entityId)
            customScreenWidget.id
        } else {
            -1
        }
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }
}