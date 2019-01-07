package worlds.gregs.hestia.game.plugins.widget.systems

import worlds.gregs.hestia.api.widget.components.FullScreenWidget
import worlds.gregs.hestia.network.out.WindowsPane
import worlds.gregs.hestia.services.send
import kotlin.reflect.KClass

abstract class BaseFullScreen(widget: KClass<out FullScreenWidget>) : BaseWidget(widget) {

    override fun open(entityId: Int) {
        es.send(entityId, WindowsPane(id, 0))
    }

    override fun close(entityId: Int) {
    }

}