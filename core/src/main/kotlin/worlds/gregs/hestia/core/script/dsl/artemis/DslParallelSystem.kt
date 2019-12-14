package worlds.gregs.hestia.core.script.dsl.artemis

import com.artemis.Aspect
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import worlds.gregs.hestia.artemis.ParallelSystem

class DslParallelSystem(aspect: Aspect.Builder, private val initialize: (() -> Unit)?, private val dispose: (() -> Unit)?, private val process: (Int) -> Unit, private val inserted: ((Int) -> Unit)?, private val removed: ((Int) -> Unit)?) : ParallelSystem(aspect) {

    override fun initialize() {
        initialize?.invoke()
    }

    override fun inserted(entityId: Int) {
        inserted?.invoke(entityId)
    }

    override fun processAsync(entityId: Int) = GlobalScope.async {
        process.invoke(entityId)
    }

    override fun removed(entityId: Int) {
        removed?.invoke(entityId)
    }

    override fun dispose() {
        dispose?.invoke()
    }

}