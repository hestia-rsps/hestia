package worlds.gregs.hestia.game.plugins.widget.systems

import worlds.gregs.hestia.api.widget.components.FullScreenWidget
import worlds.gregs.hestia.network.game.out.WindowsPane
import worlds.gregs.hestia.services.send
import kotlin.reflect.KClass

abstract class BaseFullScreen(widget: KClass<out FullScreenWidget>) : BaseWidget(widget) {

    override fun open(entityId: Int) {
        es.send(entityId, WindowsPane(getId(entityId), 0))
    }

    override fun close(entityId: Int) {
    }

}