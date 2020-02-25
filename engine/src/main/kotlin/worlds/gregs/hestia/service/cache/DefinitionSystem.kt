package worlds.gregs.hestia.service.cache

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.cache.definition.Definition
import world.gregs.hestia.cache.definition.DefinitionReader

abstract class DefinitionSystem<T : Definition> : PassiveSystem() {

    abstract val reader: DefinitionReader<T>

    fun get(id: Int, member: Boolean = true) : T = reader.get(id, member)

}