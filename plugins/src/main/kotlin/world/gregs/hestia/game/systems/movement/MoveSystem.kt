package world.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.Move
import world.gregs.hestia.game.component.movement.Moving
import world.gregs.hestia.services.Aspect

/**
 * MoveSystem
 * Instantly moves player to [Position]
 * Aka teleporting without delay & animations
 */
class MoveSystem : IteratingSystem(Aspect.all(Position::class, Move::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var moveMapper: ComponentMapper<Move>

    private lateinit var movingMapper: ComponentMapper<Moving>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val move = moveMapper.get(entityId)
//        val lastPlane = position.plane

        //Move to location
        position.x = move.x
        position.y = move.y
        position.plane = move.plane

        //Remove command
        moveMapper.remove(entityId)

        //Flag movement type as "move"
        movingMapper.create(entityId)

        /*entity.updateEntityRegion()
        if (needMapUpdate()) {
            entity.map.loadMapRegions()
        } else if (entity is PlayerBlob && lastPlane != entity.plane) {
            entity.clientLoadedMapRegion = false
        }*/
//        steps.reset()
    }

}