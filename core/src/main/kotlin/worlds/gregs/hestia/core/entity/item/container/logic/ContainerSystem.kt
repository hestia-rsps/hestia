package worlds.gregs.hestia.core.entity.item.container.logic

import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import kotlin.reflect.KClass

abstract class ContainerSystem(container: KClass<out ItemContainer>) : IteratingSystem(Aspect.all(NetworkSession::class, container)) {

    abstract val stackType: StackType

    override fun process(entityId: Int) {
        val container = getContainer(entityId)
        if(container.refresh) {
            update(entityId)
        }
    }

    abstract fun update(entityId: Int)

    abstract fun getContainer(entityId: Int): ItemContainer
}