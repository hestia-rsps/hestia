package worlds.gregs.hestia.game.map.container

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.services.Aspect

class PlayerUpdateSystem : IteratingSystem(Aspect.all(Player::class, Shift::class)) {

    private lateinit var map: PlayerMap
    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun process(entityId: Int) {
        val shift = shiftMapper.get(entityId)
        map.update(entityId, shift)
    }

}