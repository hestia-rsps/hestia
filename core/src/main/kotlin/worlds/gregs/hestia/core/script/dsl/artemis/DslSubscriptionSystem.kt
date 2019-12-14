package worlds.gregs.hestia.core.script.dsl.artemis

import com.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem

class DslSubscriptionSystem(aspect: Aspect.Builder, private val initialize: (() -> Unit)?, private val dispose: (() -> Unit)?, private val inserted: ((Int) -> Unit)?, private val removed: ((Int) -> Unit)?) : SubscriptionSystem(aspect) {

    override fun initialize() {
        initialize?.invoke()
    }

    override fun inserted(entityId: Int) {
        inserted?.invoke(entityId)
    }

    override fun removed(entityId: Int) {
        removed?.invoke(entityId)
    }

    override fun dispose() {
        dispose?.invoke()
    }

}