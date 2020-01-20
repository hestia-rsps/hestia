package worlds.gregs.hestia.core.entity.item.container.logic

import arrow.core.right
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.entity.item.container.api.*
import worlds.gregs.hestia.core.entity.item.container.model.ContainerList
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class ContainerSystem : Containers() {

    lateinit var definitions: ItemDefinitionSystem
    lateinit var containerListMapper: ComponentMapper<ContainerList>
    private lateinit var es: EventSystem

    override fun send(entityId: Int, interfaceId: Int) {
        val containerType = ContainerType.values().firstOrNull { it.interfaceId == interfaceId } ?: return
        val container = getContainer(entityId, containerType.containerId) ?: return
        es.send(entityId, InterfaceItems(containerType.containerId, container.map { if (it == null) Pair(-1, 0) else Pair(it.type, it.amount) }))
    }

    override fun getContainer(entityId: Int, id: Int): Container? {
        val containerList = containerListMapper.get(entityId)
        return containerList.containers[id]
    }

    override fun hasContainer(entityId: Int, id: Int): Boolean {
        val containerList = containerListMapper.get(entityId)
        return containerList.containers.containsKey(id)
    }

    @Suppress("RemoveExplicitTypeArguments")
    override fun modify(entityId: Int, id: Int, composition: Composition, overflow: Boolean): ItemResult {
        val container = getContainer(entityId, id) ?: return ItemResult.Issue.Invalid
        val containerType = ContainerType.values().firstOrNull { it.containerId == id }
                ?: return ItemResult.Issue.Invalid
        val modification = ContainerModificationDetails(container.clone().right(), definitions.reader, mutableListOf(), containerType.type)
        val (result, _, overflows) = composition.invoke(modification)
        return result.fold<ItemResult>({ it }, {
            if (!overflow && overflows.isNotEmpty()) {
                return@fold ItemResult.Issue.Full
            }
            if (setItems(entityId, id, it)) {
                if (overflows.isNotEmpty()) {
                    return@fold ItemResult.Success.Overflow(overflows)
                }
                ItemResult.Success.Success
            } else {
                ItemResult.Issue.Invalid
            }
        })
    }

    private fun setItems(entityId: Int, id: Int, replacement: Container): Boolean {
        val containerList = containerListMapper.get(entityId)

        if (!containerList.containers.containsKey(id) || containerList.containers[id]!!.size != replacement.size) {
            return false
        }

        containerList.containers[id] = replacement
        containerList.refreshes.add(id)
        return true
    }
}