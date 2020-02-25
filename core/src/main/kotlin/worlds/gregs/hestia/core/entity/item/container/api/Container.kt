package worlds.gregs.hestia.core.entity.item.container.api

import arrow.core.Either
import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.StackType

data class ContainerModificationDetails(val either: Either<ItemResult.Issue, Container>, val definitions: DefinitionReader<ItemDefinition>, val overflows: MutableList<Item>, val stackType: StackType)
typealias Composition = (ContainerModificationDetails) -> ContainerModificationDetails
typealias Container = Array<Slot>
typealias Slot = Item?