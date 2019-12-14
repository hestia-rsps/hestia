package worlds.gregs.hestia.core.display.widget.logic.systems

import worlds.gregs.hestia.core.display.widget.model.components.FullScreenWidget
import worlds.gregs.hestia.network.client.encoders.messages.WidgetWindowsPane
import worlds.gregs.hestia.artemis.send
import kotlin.reflect.KClass

abstract class BaseFullScreen(widget: KClass<out FullScreenWidget>) : BaseWidget(widget) {

    override fun open(entityId: Int) {
        es.send(entityId, WidgetWindowsPane(getId(entityId), 0))
    }

    override fun close(entityId: Int) {
    }

}