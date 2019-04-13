package worlds.gregs.hestia.game.plugins.entity.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.client.update.components.Damage
import worlds.gregs.hestia.artemis.events.Hit
import worlds.gregs.hestia.game.client.update.block.Marker

class DamageSystem : PassiveSystem() {
    private lateinit var damageMapper: ComponentMapper<Damage>

    @Subscribe
    fun hit(event: Hit) {
        val entityId = event.entityId
        val damage = damageMapper.create(entityId)
        //TODO soak damage change
        damage.hits.add(Marker(event.source, event.amount, event.mark, event.delay, event.critical, event.soak))
    }
}