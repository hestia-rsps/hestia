package worlds.gregs.hestia.core.entity.item.container.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer

abstract class Containers : PassiveSystem() {

    abstract fun send(entityId: Int, interfaceId: Int)

    abstract fun getContainer(entityId: Int, id: Int): Container?

    abstract fun hasContainer(entityId: Int, id: Int): Boolean

    /**
     * Applies one or many changes to an [ItemContainer], stops if one fails.
     * Note: changes only apply if returns [ItemResult.Success]
     * @param entityId The id of the entity who's container to modify
     * @param id The container id to change
     * @param composition The container modifications to make.
     * @param overflow Whether addition overflows should be applied or not (returns [ItemResult.Issue.Full] instead)
     * @return [ItemResult] success (with params) or failure
     */
    abstract fun modify(entityId: Int, id: Int, composition: Composition, overflow: Boolean = true): ItemResult

}