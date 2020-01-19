package worlds.gregs.hestia.core.action.logic.systems

import com.artemis.BaseSystem
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.action.model.WorldAction

class ActionSystem : PassiveSystem() {

    /**
     * We need to hack the world here as the one in [BaseSystem] world is protected and so can't be reached internally for
     * [EventSystem] extension.
     */
    @Subscribe(priority = Int.MAX_VALUE)
    private fun handleAction(action: WorldAction) {
        action.world = world
    }

}