package worlds.gregs.hestia.api.widget

import com.artemis.Component
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

abstract class Widget(component: KClass<out Component>) : SubscriptionSystem(Aspect.all(component)) {
    open val frame = false

    abstract fun getId(entityId: Int): Int

    open fun getWindow(entityId: Int): Int? {
        return null
    }

    open fun getIndex(entityId: Int): Int {
        return 0
    }

    abstract fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int)

    abstract fun open(entityId: Int)

    abstract fun close(entityId: Int)

}