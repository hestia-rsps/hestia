package worlds.gregs.hestia.core.entity.item.container.logic

import arrow.core.*
import arrow.fx.IO
import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.item.container.api.*
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.addEmptySlot
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.canStack
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.indexOf
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.unfold
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.unfoldRight
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import java.lang.Math.addExact
import java.lang.Math.subtractExact
import java.util.*
import java.util.function.Predicate
import kotlin.math.abs

/**
 * Empty [Composition] for folding chained modifications
 */
val blankComposition: Composition = { it }

data class ContainerTransformBuilder(val overflow: Boolean = false) {
    lateinit var function: Composition
    lateinit var type: ContainerType
}

fun EntityAction.transform(builder: ContainerTransformBuilder): ItemResult {
    val containers = system(ContainerSystem::class)
    return containers.modify(entity, builder.type, builder.function, builder.overflow)
}

/**
 * Applies one or many changes to an [ItemContainer], stops if one fails.
 * Note: changes only apply if returns [ItemResult.Success]
 * @param definitions Item definitions
 * @param function The container modifications to make.
 * @param overflow Whether addition overflows should be applied or not (returns [ItemResult.Issue.Full] instead)
 * @return [ItemResult] success (with params) or failure
 */
@Suppress("RemoveExplicitTypeArguments")
fun ItemContainer.transform(definitions: DefinitionReader<ItemDefinition>, stackType: StackType, function: Composition, overflow: Boolean = true): ItemResult {
    val modification = ContainerModificationDetails(items.clone().right(), definitions, mutableListOf(), stackType)
    val (result, _, overflows) = function.invoke(modification)
    return result.fold<ItemResult>({ it }, {
        if (!overflow && overflows.isNotEmpty()) {
            return@fold ItemResult.Issue.Full
        }
        if (setItems(it)) {
            if (overflows.isNotEmpty()) {
                return@fold ItemResult.Success.Overflow(overflows)
            }
            ItemResult.Success.Success
        } else {
            ItemResult.Issue.Invalid
        }
    })
}


fun clear(): Composition = {
    unfold(it) { container ->
        container.indices.fold(blankComposition) { acc, type -> acc andThen remove(index = type) }(it).either
    }
}

/**
 * Container modification - Addition
 * Adds a new [Item] to a [Container]. Combining stack sizes if [type] is stackable and [Container] has a stack.
 * @param type The type of item to add
 * @param amount The item stack size
 * @param index The container index to apply to (-1 = any slot)
 */
fun add(type: Int, amount: Int = 1, index: Int = -1): Composition = {
    val (_, definitions, overflows, stack) = it
    unfold(it) { container ->
        if (amount <= 0) {
            return@unfold ItemResult.Issue.Invalid.left()
        }

        val stackable = canStack(definitions, stack, type)
        if (stackable) {
            val existing = indexOf(container, type, index)
            if (existing != -1) {
                val current = container[existing]!!
                //Combine two amounts
                val result = ContainerEditor.addToStack(current, amount).fold({ overflow ->
                    overflows.add(overflow)
                    Item(type, Int.MAX_VALUE)//Continue to add maximum amount possible
                }, { item -> item })
                //Set value
                container[existing] = result
                container.right()
            } else {
                addEmptySlot(container, type, amount, index)
            }
        } else {
            if (index != -1 && amount > 1) {
                return@unfold ItemResult.Issue.Invalid.left()
            }
            var result: Either<ItemResult.Issue.Full, Container> = container.right()
            for (remaining in 1..amount) {
                result = addEmptySlot(container, type, 1, index)
                if (result.isLeft()) {
                    break
                }
            }
            result
        }
    }
}

/**
 * Helper function for easily adding multiple non-stackable items
 * @param types List of item types to add
 */
fun addAll(vararg types: Int): Composition = types.fold(blankComposition) { acc, type -> acc andThen add(type) }

/**
 * Helper function for easily adding multiple item amounts
 * @param pairs List of item type & amounts to add
 */
fun addAll(vararg pairs: Pair<Int, Int>): Composition = pairs.fold(blankComposition) { acc, (type, amount) -> acc andThen add(type, amount) }

/**
 * Container modification - Subtraction
 * Removes a [Item] from a [Container]. Subtracting from stack size if [type] is stackable and [Container] has a stack.
 * @param type The type of item to remove
 * @param amount The amount of items to remove
 * @param index The container index to apply to (-1 = any slot)
 */
fun remove(type: Int = -1, amount: Int = 1, index: Int = -1): Composition = {
    val (_, definitions, _, stack) = it
    unfold(it) { container ->
        if (amount <= 0) {
            return@unfold ItemResult.Issue.Invalid.left()
        }

        val stackable = canStack(definitions, stack, type)
        if (stackable) {
            val has = indexOf(container, type, index)
            if (has != -1) {
                val current = container[has]!!
                //Subtract two amounts
                val result = ContainerEditor.subtractFromStack(current, amount)
                result.fold({ issue -> issue.left() }, { item ->
                    container[has] = item//Set value
                    container.right()
                })
            } else {
                ItemResult.Issue.Underflow(Item(type, amount)).left()
            }
        } else {
            //Removing more than 1 at a single index is not possible
            if (index != -1 && amount > 1) {
                return@unfold ItemResult.Issue.Invalid.left()
            }

            for (remaining in 1..amount) {
                val has = indexOf(container, type, index)
                if (has != -1) {
                    container[has] = null//Remove item
                } else {
                    return@unfold ItemResult.Issue.Underflow(Item(type, remaining)).left()
                }
            }
            container.right()
        }
    }
}

/**
 * Helper function for easily removing multiple non-stackable items
 * @param types List of item types to remove
 */
fun removeAll(vararg types: Int): Composition = types.fold(blankComposition) { acc, type -> acc andThen remove(type) }

/**
 * Helper function for easily removing multiple item amounts
 * @param pairs List of item type & amounts to remove
 */
fun removeAll(vararg pairs: Pair<Int, Int>): Composition = pairs.fold(blankComposition) { acc, (type, amount) -> acc andThen remove(type, amount) }

/**
 * Helper function for removing one non-stackable item and replacing it with another.
 * @param index The first item slot
 * @param with The second item slot
 */
fun replace(type: Int, with: Int, index: Int = -1): Composition = remove(type, index = index) andThen add(with, index = index)

/**
 * Helper function for easily replacing multiple non-stackable items
 * @param pairs List of item & replacement types
 */
fun replaceAll(vararg pairs: Pair<Int, Int>): Composition = pairs.fold(blankComposition) { acc, (type, amount) -> acc andThen replace(type, amount) }

/**
 * Switches two slots irrespective to what items they might have.
 * @param index The first item slot
 * @param with The second item slot
 */
fun switch(index: Int, with: Int): Composition = {
    unfold(it) { container ->
        if(index == with || index !in container.indices || with !in container.indices) {
            return@unfold ItemResult.Issue.Invalid.left()
        }

        val temp = container[index]
        container[index] = container[with]
        container[with] = temp
        container.right()
    }
}

/**
 * Sorts a list of options pushing null's to the back while keeping the same order of non-null's
 */
fun Container.sort(): Container {
    val list = LinkedList<Slot>()
    for (i in lastIndex downTo 0) {
        val a = get(i)
        if (a == null) {
            list.addLast(a)
        } else {
            list.addFirst(a)
        }
    }
    return list.toTypedArray()
}

object ContainerEditor {

    /**
     * Checks if item [type] can be stacked, based on the [Container] [StackType]
     * @param definitions The item definitions
     * @param stackType The type of stacking for the container
     * @param type The item type to check
     * @return [Boolean] whether this item should be stacked or not
     */
    internal fun canStack(definitions: DefinitionReader<ItemDefinition>, stackType: StackType, type: Int): Boolean {
        return when (stackType) {
            StackType.Normal -> definitions.get(type).stackable == 1
            StackType.Always -> true
            StackType.Never -> false
        }
    }

    /**
     * Helper function, [unfoldRight] the current [Container] and combines back to a [ContainerModificationDetails] after
     * @return [ContainerModificationDetails]
     */
    internal fun unfold(modification: ContainerModificationDetails, block: (Container) -> Either<ItemResult.Issue, Container>): ContainerModificationDetails {
        val (either, definitions, overflows, stack) = modification
        val newEither = unfoldRight(either) { container ->
            block.invoke(container)
        }
        return ContainerModificationDetails(newEither, definitions, overflows, stack)
    }

    internal fun <I, T> unfoldRight(either: Either<I, T>, f: (T) -> Either<I, T>) = either.fold({ Left(it) }, { f(it) })

    /**
     * Adds current [Item] stack size with [amount]
     * Note: if combined values exceed [Int.MAX_VALUE] then overflow amount is returned left instead.
     * @param current The current item to increase
     * @param amount The amount to add
     * @return [Either] [Item] overflow item or [Item] New item instance with combined stack sizes.
     */
    internal fun addToStack(current: Item, amount: Int): Either<Item, Item> {
        return addSafe(current.amount, amount).fold({
            //If exceeds maximum value
            val maximum = Int.MAX_VALUE - current.amount//Calculate maximum amount that can be added
            Item(current.type, amount - maximum).left()//Add amount that can't be added to overflow list
        }, {
            Item(current.type, it).right()
        })
    }

    /**
     * Adds two values accounting for Integer overflow
     * @param currentAmount The first value
     * @param addition The second value
     * @return [Either] [ArithmeticException] if exceeds [Int.MAX_VALUE] or combined value [Int]
     */
    private fun addSafe(currentAmount: Int, addition: Int): Either<Throwable, Int> {
        return IO { addExact(currentAmount, addition) }.attempt().unsafeRunSync()
    }

    /**
     * Subtracts [amount] from current [Item] stack size
     * Note: if subtracted result is less than zero then underflow is returned left
     *      if equals exactly zero then null is returned right
     * @param current The current item to subtract from
     * @param amount The amount to subtract
     * @return [Item] New item instance with new stack size.
     */
    internal fun subtractFromStack(current: Item, amount: Int): Either<ItemResult.Issue.Underflow, Item?> {
        return subtractSafe(current.amount, amount).fold({
            //If deceeds minimum value
            val maximum = current.amount + Int.MIN_VALUE//Calculate maximum amount that can be subtracted
            ItemResult.Issue.Underflow(Item(current.type, amount - maximum)).left()
        }, {
            when {
                it < 0 -> ItemResult.Issue.Underflow(Item(current.type, abs(it))).left()
                it == 0 -> null.right()
                else -> Item(current.type, it).right()
            }
        })
    }

    /**
     * Subtracts two values accounting for Integer overflow
     * @param currentAmount The first value
     * @param addition The second value
     * @return [Either] [ArithmeticException] if exceeds [Int.MAX_VALUE] or combined value [Int]
     */
    private fun subtractSafe(currentAmount: Int, addition: Int): Either<Throwable, Int> {
        return IO { subtractExact(currentAmount, addition) }.attempt().unsafeRunSync()
    }

    /**
     * Adds [Item] to empty slot
     * @param container The container to add too
     * @param type The item type
     * @param amount The item stack size
     * @param index The exact index to check or -1 to check all indices.
     * @return [Either] [ItemResult.Issue.Full] or [Container] with addition applied
     */
    internal fun addEmptySlot(container: Container, type: Int, amount: Int, index: Int): Either<ItemResult.Issue.Full, Container> {
        val emptyIndex = indexOfEmpty(container, index)
        return if (emptyIndex != -1) {
            container[emptyIndex] = Item(type, amount)
            container.right()
        } else {
            ItemResult.Issue.Full.left()
        }
    }

    private val emptyPredicate = Predicate<Item?> { it == null }

    /**
     * Checks a [Container] slot(s) for a null index.
     * @param container The container to check
     * @param index The exact index to check or -1 to check all indices.
     * @return [Int] index of an empty slot or -1
     */
    fun indexOfEmpty(container: Container, index: Int) = indexOf(container, index, emptyPredicate)

    /**
     * Checks a [Container] slot(s) for an index of [type].
     * @param container The container to check
     * @param type The type of item to check for.
     * @param index The exact index to check or -1 to check all indices.
     * @return [Int] index of the first slot with [type] or -1
     */
    fun indexOf(container: Container, type: Int, index: Int) = indexOf(container, index, Predicate { type == -1 || it?.type == type })

    /**
     * Checks a [Container] slot(s) against [predicate].
     * @param container The container to check
     * @param index The exact index to check or -1 to check all indices.
     * @param predicate The predicate to check against.
     * @return [Int] index of the first slot matching [predicate] or -1
     */
    fun indexOf(container: Container, index: Int, predicate: Predicate<Item?>): Int {
        return if (index != -1) {
            if (index in container.indices && predicate.test(container[index])) {
                index
            } else {
                -1
            }
        } else {
            container.indexOfFirst { predicate.test(it) }
        }
    }
}