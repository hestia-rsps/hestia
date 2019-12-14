package worlds.gregs.hestia.core.entity.entity.logic.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.update.model.components.Damage
import worlds.gregs.hestia.core.entity.entity.model.events.Hit
import worlds.gregs.hestia.game.update.blocks.Marker

class DamageSystem : PassiveSystem() {
    private lateinit var damageMapper: ComponentMapper<Damage>

    @Subscribe
    private fun hit(event: Hit) {
        val entityId = event.entityId
        val damage = damageMapper.create(entityId)
        //TODO soak damage change
        damage.hits.add(Marker(event.source, event.amount, event.mark, event.delay, event.critical, event.soak))
    }
}