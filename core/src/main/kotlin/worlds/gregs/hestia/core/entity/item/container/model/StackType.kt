package worlds.gregs.hestia.core.entity.item.container.model

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