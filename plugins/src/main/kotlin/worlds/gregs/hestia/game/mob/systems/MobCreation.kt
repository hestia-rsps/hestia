package worlds.gregs.hestia.game.mob.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.entity.Type
import worlds.gregs.hestia.game.events.CreateMob
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem

class MobCreation : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>

    @Subscribe
    fun create(event: CreateMob): Int {
        val entityId = EntityFactory.create(worlds.gregs.hestia.game.mob.archetypes.MobFactory::class)

        val type = typeMapper.get(entityId)
        type.id = event.mobId

        val position = positionMapper.get(entityId)
        position.set(event.x, event.y, event.plane)
        return entityId
    }
}