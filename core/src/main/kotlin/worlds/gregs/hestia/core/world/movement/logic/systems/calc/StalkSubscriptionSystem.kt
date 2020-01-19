package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem.Companion.withinContact
import worlds.gregs.hestia.core.world.movement.model.components.calc.Beside
import worlds.gregs.hestia.core.world.movement.model.components.calc.Stalk

/**
 * StalkSubscriptionSystem
 * Adds/removes watching the entity being followed when [Stalk] is inserted/removed
 */
class StalkSubscriptionSystem : SubscriptionSystem(Aspect.all(Position::class, Stalk::class)) {
    private lateinit var stalkMapper: ComponentMapper<Stalk>
    private lateinit var besideMapper: ComponentMapper<Beside>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var es: EventSystem

    override fun inserted(entityId: Int) {
        //Watch
        val stalk = stalkMapper.get(entityId)
        es.perform(entityId, Watch(stalk.entity))

        val position = positionMapper.get(entityId)
        val targetPosition = positionMapper.get(stalk.entity)

        val width = sizeMapper.width(stalk.entity)
        val height = sizeMapper.height(stalk.entity)

        if(withinContact(position.x, position.y, targetPosition.x, targetPosition.y, width, height)) {
            return
        }

        //Walk beside (if not already)
        besideMapper.create(entityId).apply {
            this.x = targetPosition.x
            this.y = targetPosition.y
            this.sizeX = width
            this.sizeY = height
            this.calculate = true
            this.beside = true
        }
    }

    override fun removed(entityId: Int) {
        es.perform(entityId, Watch(-1))
    }
}