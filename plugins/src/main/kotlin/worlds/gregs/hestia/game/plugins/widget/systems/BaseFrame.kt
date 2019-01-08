package worlds.gregs.hestia.game.plugins.widget.systems

import worlds.gregs.hestia.api.widget.components.Frame
import kotlin.reflect.KClass

abstract class BaseFrame(frame: KClass<out Frame>) : BaseWidget(frame) {
    override val frame = true
}