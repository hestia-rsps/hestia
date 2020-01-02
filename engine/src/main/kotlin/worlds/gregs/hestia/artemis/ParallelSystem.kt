package worlds.gregs.hestia.artemis

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import java.util.*

abstract class ParallelSystem(aspect: Aspect.Builder) : BaseEntitySystem(aspect) {

    private val processed = LinkedList<Deferred<*>>()

    override fun processSystem() = runBlocking {
        val actives = subscription.entities

        actives.forEach {
            processed.add(processAsync(it))
        }

        while (processed.isNotEmpty()) {
            processed.poll().await()
        }
    }

    abstract fun processAsync(entityId: Int): Deferred<*>

}