package worlds.gregs.hestia.core.plugins.movement.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.mob.MobChunk
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.player.PlayerChunk
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.core.plugins.movement.components.calc.Follow
import worlds.gregs.hestia.core.plugins.movement.components.calc.Step
import worlds.gregs.hestia.game.client.update.block.DirectionUtils.Companion.getOffset
import worlds.gregs.hestia.api.client.update.components.Moving
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.mobs
import worlds.gregs.hestia.services.players
import worlds.gregs.hestia.services.toArray

@Wire(failOnNull = false)
class FollowSystem : IteratingSystem(Aspect.all(Position::class, Shift::class)) {
    private lateinit var followMapper: ComponentMapper<Follow>
    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepMapper: ComponentMapper<Step>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private var playerChunk: PlayerChunk? = null
    private var mobChunk: MobChunk? = null

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)

        //Get all followers
        val players = (playerChunk?.get(position) ?: world.players().toArray().asList()).filter { followMapper.has(it) && followMapper.get(it).entity == entityId }
        val mobs = (mobChunk?.get(position) ?: world.mobs().toArray().asList()).filter { followMapper.has(it) && followMapper.get(it).entity == entityId }

        //TODO a nicer way of getting all and testing getting entities
        val entities = players.union(mobs)

        val shift = shiftMapper.get(entityId)

        //Cancel follow if target changes plane or instant moves
        if(shift.plane != 0 || movingMapper.has(entityId)) {
            entities.forEach {
                followMapper.remove(it)
            }
            return
        }

        //Position without shift, but applying and extra offset from moving multiple tiles (running)
        val targetX = position.x + (shift.x - getOffset(0, shift.x))
        val targetY = position.y + (shift.y - getOffset(0, shift.y))
        //Move to last position
        entities.forEach {
            stepMapper.create(it).apply {
                x = targetX
                y = targetY
            }
        }
    }
}