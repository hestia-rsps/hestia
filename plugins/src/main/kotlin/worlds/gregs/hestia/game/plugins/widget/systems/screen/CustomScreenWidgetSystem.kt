package worlds.gregs.hestia.game.plugins.widget.systems.screen

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.widget.components.screen.CustomScreenWidget
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class CustomScreenWidgetSystem : BaseScreen(CustomScreenWidget::class) {
    override var id = -1

    private lateinit var customScreenWidgetMapper: ComponentMapper<CustomScreenWidget>

    override fun inserted(entityId: Int) {
        val customScreenWidget = customScreenWidgetMapper.get(entityId)
        id = customScreenWidget.id
        super.inserted(entityId)
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }
}