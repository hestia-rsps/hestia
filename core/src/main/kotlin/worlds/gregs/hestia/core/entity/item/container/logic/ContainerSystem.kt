package worlds.gregs.hestia.core.entity.item.container.logic

import arrow.core.right
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceRefresh
import worlds.gregs.hestia.core.entity.item.container.api.*
import worlds.gregs.hestia.core.entity.item.container.model.ContainerList
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems
import worlds.gregs.hestia.service.cache.config.definitions.ItemContainerDefinition
import worlds.gregs.hestia.service.cache.config.systems.ItemContainerDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class ContainerSystem : Containers() {

    lateinit var containerDefinitions: ItemContainerDefinitionSystem
    lateinit var itemDefinitions: ItemDefinitionSystem
    lateinit var containerListMapper: ComponentMapper<ContainerList>
    private lateinit var es: EventSystem

    override fun send(entityId: Int, interfaceId: Int) {
        val containerType = ContainerType.values().firstOrNull { it.interfaceId == interfaceId } ?: return
        val container = getContainer(entityId, containerType)
        es.send(entityId, InterfaceItems(containerType.containerId, container.map { if (it == null) Pair(-1, 0) else Pair(it.type, it.amount) }))
    }

    override fun getContainer(entityId: Int, id: Int): Container? {
        val type = ContainerType.values().firstOrNull { it.containerId == id } ?: return null
        return getContainer(entityId, type)
    }

    private fun createContainer(type: ContainerType): Container {
        val defs = containerDefinitions.get(type.containerId)
        return arrayOfNulls(defs.length)
    }

    override fun getContainer(entityId: Int, type: ContainerType): Container {
        val containerList = containerListMapper.get(entityId)
        return containerList.containers.getOrPut(type.containerId) { createContainer(type) }
    }

    override fun hasContainer(entityId: Int, type: ContainerType): Boolean {
        val containerList = containerListMapper.get(entityId)
        return containerList.containers.containsKey(type.containerId)
    }

    override fun modify(entityId: Int, vararg modifications: Pair<ContainerType, Composition>): ItemResult {
        modifications.map { (type, composition) ->
            val container = getContainer(entityId, type)
            val modification = ContainerModificationDetails(container.clone().right(), itemDefinitions.reader, mutableListOf(), type.type)
            val (result, _, _) = composition.invoke(modification)
            result.fold<ItemResult>({ return it }, { return@map type to it })
            type to container
        }.forEach { (type, container) ->
            if (!setItems(entityId, type, container)) {
                throw IllegalStateException("Real serious error here.")
            }
        }
        return ItemResult.Success.Success
    }

    @Suppress("RemoveExplicitTypeArguments")
    override fun modify(entityId: Int, type: ContainerType, composition: Composition, overflow: Boolean): ItemResult {
        val container = getContainer(entityId, type)
        val modification = ContainerModificationDetails(container.clone().right(), itemDefinitions.reader, mutableListOf(), type.type)
        val (result, _, overflows) = composition.invoke(modification)
        return result.fold<ItemResult>({ it }, {
            if (!overflow && overflows.isNotEmpty()) {
                return@fold ItemResult.Issue.Full
            }
            if (setItems(entityId, type, it)) {
                if (overflows.isNotEmpty()) {
                    return@fold ItemResult.Success.Overflow(overflows)
                }
                ItemResult.Success.Success
            } else {
                ItemResult.Issue.Invalid
            }
        })
    }


    private fun setItems(entityId: Int, type: ContainerType, replacement: Container): Boolean {
        val containerList = containerListMapper.get(entityId)

        if (!containerList.containers.containsKey(type.containerId) || containerList.containers[type.containerId]!!.size != replacement.size) {
            return false
        }

        containerList.containers[type.containerId] = replacement
        es.perform(entityId, InterfaceRefresh(type.interfaceId))
        return true
    }
}