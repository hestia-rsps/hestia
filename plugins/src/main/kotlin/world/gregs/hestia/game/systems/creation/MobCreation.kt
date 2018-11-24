package world.gregs.hestia.game.systems.creation

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import world.gregs.hestia.game.archetypes.EntityFactory
import world.gregs.hestia.game.archetypes.MobFactory
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.entity.Type
import world.gregs.hestia.game.events.CreateMob
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import java.util.concurrent.atomic.AtomicInteger

class MobCreation : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>

    @Subscribe
    fun create(event: CreateMob): Int {
        val entityId = EntityFactory.create(MobFactory::class)

        val type = typeMapper.get(entityId)
        type.id = event.mobId

        val position = positionMapper.get(entityId)
        position.x = event.x
        position.y = event.y
        position.plane = event.plane
        return entityId
    }
}