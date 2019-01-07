package worlds.gregs.hestia.api.widget

import com.artemis.Component
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

abstract class Widget(component: KClass<out Component>) : SubscriptionSystem(Aspect.all(component)) {
    open val frame = false

    abstract var id: Int

    open fun getId(entityId: Int): Int {
        return id
    }

    abstract fun getIndex(resizable: Boolean): Int

    abstract fun click(entityId: Int, componentId: Int, option: Int)

    abstract fun open(entityId: Int)

    abstract fun close(entityId: Int)

}