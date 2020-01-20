package worlds.gregs.hestia.core.entity.item.container.model

import com.artemis.Component
import worlds.gregs.hestia.core.entity.item.container.api.Container
import java.util.*

class ContainerList : Component() {
    val containers = mutableMapOf<Int, Container>()
    val refreshes = LinkedList<Int>()
}