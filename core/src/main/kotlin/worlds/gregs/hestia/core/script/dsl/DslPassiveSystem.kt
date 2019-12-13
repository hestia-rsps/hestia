package worlds.gregs.hestia.core.script.dsl

import net.mostlyoriginal.api.system.core.PassiveSystem

class DslPassiveSystem(private val initialize: (() -> Unit)?, private val dispose: (() -> Unit)?) : PassiveSystem() {

    override fun initialize() {
        initialize?.invoke()
    }

    override fun dispose() {
        dispose?.invoke()
    }

}