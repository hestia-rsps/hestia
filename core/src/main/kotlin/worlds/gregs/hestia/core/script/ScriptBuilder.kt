package worlds.gregs.hestia.core.script

import com.artemis.BaseSystem
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.event.ExtendedEventDispatchStrategy

abstract class ScriptBuilder : PassiveSystem() {
    /**
     * Builds a list of systems from the current script
     * @return list of [BaseSystem]s and their priorities
     */
    abstract fun build(builder: WorldConfigurationBuilder)

    /**
     * Builds a list of systems from the current script
     * @param world The world to apply subscriptions to
     * @param dispatcher The dispatcher to register event listeners with
     */
    abstract fun build(world: World, dispatcher: ExtendedEventDispatchStrategy)
}