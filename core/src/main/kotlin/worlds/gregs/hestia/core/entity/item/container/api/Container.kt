package worlds.gregs.hestia.core.entity.item.container.api

import arrow.core.Either
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition

data class ContainerModificationDetails(val either: Either<ItemResult.Issue, Container>, val definitions: DefinitionReader<ItemDefinition>, val overflows: MutableList<Item>, val stackType: StackType)
typealias Composition = (ContainerModificationDetails) -> ContainerModificationDetails
typealias Container = Array<Slot>
typealias Slot = Item?