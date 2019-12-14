package worlds.gregs.hestia.core.entity.mob.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.entity.logic.EntityFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.mob.logic.MobFactory
import worlds.gregs.hestia.core.entity.mob.model.events.CreateMob

class MobCreation : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>

    @Subscribe
    fun create(event: CreateMob): Int {
        val entityId = EntityFactory.create(MobFactory::class)

        val type = typeMapper.get(entityId)
        type.id = event.mobId

        val position = positionMapper.get(entityId)
        position.set(event.x, event.y, event.plane)
        return entityId
    }
}