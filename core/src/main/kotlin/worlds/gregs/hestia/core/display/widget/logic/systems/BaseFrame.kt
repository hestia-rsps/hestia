package worlds.gregs.hestia.core.display.widget.logic.systems

import worlds.gregs.hestia.core.display.widget.model.components.Frame
import kotlin.reflect.KClass

abstract class BaseFrame(frame: KClass<out Frame>) : BaseWidget(frame) {
    override val frame = true
}