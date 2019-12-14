package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.calc.Beside
import worlds.gregs.hestia.core.world.movement.model.components.calc.Stalk
import worlds.gregs.hestia.service.Aspect

@Wire(failOnNull = false)
class StalkSystem : IteratingSystem(Aspect.all(Mobile::class, Position::class, Stalk::class)) {
    private lateinit var stalkMapper: ComponentMapper<Stalk>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var besideMapper: ComponentMapper<Beside>
    private lateinit var sizeMapper: ComponentMapper<Size>

    override fun process(entityId: Int) {
        val stalk = stalkMapper.get(entityId)
        val targetId = stalk.entity

        //Cancel stalking if target doesn't exist
        if(!world.entityManager.isActive(targetId)) {
            stalkMapper.remove(entityId)
            return
        }

        val position = positionMapper.get(entityId)
        val targetPosition = positionMapper.get(targetId)

        //Cancel stalking if target isn't on the same plane
        if(position.plane != targetPosition.plane) {
            stalkMapper.remove(entityId)
            return
        }

        //Move to last position
        besideMapper.create(entityId).apply {
            this.x = targetPosition.x
            this.y = targetPosition.y
            this.sizeX = sizeMapper.width(targetId)
            this.sizeY = sizeMapper.height(targetId)
            this.calculate = true
            this.beside = true
        }
    }
}