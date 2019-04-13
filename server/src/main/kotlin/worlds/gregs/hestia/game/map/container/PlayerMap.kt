package worlds.gregs.hestia.game.map.container

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.Aspect

/**
 * Contains position of all players
 */
open class PlayerMap : EntityMap(Aspect.all(Player::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>

    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        insert(entityId, position.x, position.y, position.plane)
        setTile(entityId, position.locationHash30Bit)
    }

    override fun update(entityId: Int, shift: Shift) {
        val position = positionMapper.get(entityId)
        update(entityId, position.x, position.y, position.plane)
        setTile(entityId, position.locationHash30Bit)
        removeFromTile(entityId, Position.hash(position.x - shift.x, position.y - shift.y, position.plane - shift.plane))
    }

    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        remove(entityId, position.plane)
        removeFromTile(entityId, position.locationHash30Bit)
    }

}