package worlds.gregs.hestia.game.plugins.player.systems.region

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.region.RegionPriority
import worlds.gregs.hestia.game.events.PlayerRegionChanged

@Wire(failOnNull = false)
class PlayerRegionSystem : PassiveSystem() {

    private var regionEntities: RegionPriority? = null

    @Subscribe
    private fun event(event: PlayerRegionChanged) {
        change(event.entityId, event.from, event.to)
    }

    private fun change(entityId: Int, from: Int?, to: Int?) {
        if(from != null) {
            regionEntities?.remove(from)
        }
        if(to != null) {
            regionEntities?.add(to)
        }
    }
}