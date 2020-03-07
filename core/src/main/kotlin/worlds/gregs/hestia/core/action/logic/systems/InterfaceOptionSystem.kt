package worlds.gregs.hestia.core.action.logic.systems

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.cache.definition.definitions.InterfaceComponentDefinition
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.game
import worlds.gregs.hestia.service.cache.definition.systems.InterfaceDefinitionSystem

private typealias InterfaceSetup = InterfaceOptionBuilder.() -> Unit
private typealias InterfaceAction = EntityActions.(hash: Int, from: Int, to: Int, option: Int) -> Unit

class InterfaceOptionSystem : PassiveSystem() {

    val options = mutableMapOf<Int, MutableMap<Int, InterfaceAction>>()

    fun add(id: Int, option: Int, action: InterfaceAction) {
        options.getOrPut(option) { mutableMapOf() }[id] = action
    }

    fun get(option: Int, id: Int): InterfaceAction? {
        val actions = options[option] ?: return null
        return actions[id] ?: options[-1]?.get(id)
    }
}

class InterfaceOptionBuilder(var action: (InterfaceAction)? = null) {
    fun then(action: InterfaceAction) {
        this.action = action
    }
}

infix fun Int.hash(component: Int): Int {
    return this shl 16 or component
}

fun getInterfaceId(hash: Int) = hash shr 16

fun getInterfaceComponentId(hash: Int) = hash and 0xffff

fun on(o: InterfaceOption, id: Int, vararg components: Int, setup: InterfaceSetup) {
    addAction(id, components, getAction(setup))
}

fun on(o: InterfaceOption, option: String, vararg ids: Int, setup: InterfaceSetup) {
    ids.forEach {
        addAction(option, it, IntArray(0), getAction(setup))
    }
}

fun on(o: InterfaceOption, option: String, id: Int, vararg components: Int, setup: InterfaceSetup) {
    addAction(option, id, components, getAction(setup))
}

fun on(o: InterfaceOption, option: Int, id: Int, vararg components: Int, setup: InterfaceSetup) {
    addAction(option, id, components, getAction(setup))
}

fun on(o: InterfaceOption, id: Int, vararg components: Int, action: InterfaceAction) {
    addAction(id, components, action)
}

fun on(o: InterfaceOption, option: String, vararg ids: Int, action: InterfaceAction) {
    ids.forEach {
        addAction(option, it, IntArray(0), action)
    }
}

fun on(o: InterfaceOption, option: String, id: Int, vararg components: Int, action: InterfaceAction) {
    addAction(option, id, components, action)
}

fun on(o: InterfaceOption, option: Int, id: Int, vararg components: Int, action: InterfaceAction) {
    addAction(option, id, components, action)
}

private fun forComponents(id: Int, components: IntArray, predicate: (hash: Int, component: InterfaceComponentDefinition) -> Unit) {
    val definitions = game.getSystem(InterfaceDefinitionSystem::class)
    if (components.isEmpty()) {
        definitions.get(id).forEach { componentId, component ->
            predicate(id hash componentId, component)
        }
    } else {
        components.forEach { componentId ->
            val component = definitions.get(id)[componentId]
            if (component != null) {
                predicate(id hash componentId, component)
            }
        }
    }
}

private fun addAction(id: Int, components: IntArray, action: InterfaceAction) = forComponents(id, components) { hash, _ ->
    add(hash, -1, action)
}

private fun addAction(option: String, id: Int, components: IntArray, action: InterfaceAction) = forComponents(id, components) { hash, component ->
    component.menuActions?.forEachIndexed { index, it ->
        if (it != null && it == option) {
            add(hash, index + 1, action)
        }
    }
}

private fun addAction(option: Int, id: Int, components: IntArray, action: InterfaceAction) = forComponents(id, components) { hash, _ ->
    add(hash, option, action)
}

private fun add(hash: Int, option: Int, action: InterfaceAction) {
    val system = game.getSystem(InterfaceOptionSystem::class)
    system.add(hash, option, action)
}

private fun getAction(setup: InterfaceSetup) = InterfaceOptionBuilder().apply(setup).action!!