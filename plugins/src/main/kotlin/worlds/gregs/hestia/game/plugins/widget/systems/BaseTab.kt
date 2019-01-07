package worlds.gregs.hestia.game.plugins.widget.systems

import worlds.gregs.hestia.api.widget.Tab
import kotlin.reflect.KClass

abstract class BaseTab(tab: KClass<out Tab>) : BaseWidget(tab) {
    override val frame = true
}