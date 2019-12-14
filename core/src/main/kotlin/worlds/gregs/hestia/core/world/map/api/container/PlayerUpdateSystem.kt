package worlds.gregs.hestia.core.world.map.api.container

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.artemis.Aspect

class PlayerUpdateSystem : IteratingSystem(Aspect.all(Player::class, Shift::class)) {

    private lateinit var map: PlayerMap
    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun process(entityId: Int) {
        val shift = shiftMapper.get(entityId)
        map.update(entityId, shift)
    }

}