package worlds.gregs.hestia.core.entity.mob.logic.systems

import com.artemis.Archetype
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.mob.logic.MobFactory
import worlds.gregs.hestia.core.entity.mob.model.MobSpawns
import worlds.gregs.hestia.core.entity.mob.model.events.CreateMob

class MobCreation(private val spawn: Boolean) : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var faceMapper: ComponentMapper<Face>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = MobFactory().getBuilder().build(world)
        if(spawn) {
            MobSpawns.values().forEach {
                create(it.id, it.x, it.y, it.plane, it.direction)
            }
        }
    }

    @Subscribe
    fun create(event: CreateMob): Int {
        return create(event.mobId, event.x, event.y, event.plane, Direction.NONE)
    }

    private fun create(id: Int, x: Int, y: Int, plane: Int, direction: Direction): Int {
        val entityId = world.create(archetype)

        val type = typeMapper.get(entityId)
        type.id = id

        val position = positionMapper.get(entityId)
        position.set(x, y, plane)

        val face = faceMapper.get(entityId)
        face.x = direction.deltaX
        face.y = direction.deltaY
        if(direction != Direction.SOUTH && direction != Direction.NONE) {
            facingMapper.create(entityId)
        }
        return entityId
    }
}