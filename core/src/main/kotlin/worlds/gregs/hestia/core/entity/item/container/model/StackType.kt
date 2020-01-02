package worlds.gregs.hestia.core.entity.item.container.model

import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition

sealed class StackType {
    /**
     * Stacks based on [ItemDefinition] data
     */
    object Normal : StackType()

    /**
     * Always stacks
     */
    object Always : StackType()

    /**
     * Never stacks
     */
    object Never : StackType()
}