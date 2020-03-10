package worlds.gregs.hestia.core.entity.npc.logic.systems

import com.artemis.Archetype
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.npc.logic.NpcFactory
import worlds.gregs.hestia.core.entity.npc.model.events.CreateNpc
import worlds.gregs.hestia.game
import worlds.gregs.hestia.game.plugin.init

class NpcCreation : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = NpcFactory().getBuilder().build(world)
    }

    @Subscribe
    fun create(event: CreateNpc): Int {
        return create(event.npcId, event.x, event.y, event.plane, event.direction)
    }

    private fun create(id: Int, x: Int, y: Int, plane: Int, direction: Direction): Int {
        val entityId = world.create(archetype)

        val type = typeMapper.get(entityId)
        type.id = id

        val position = positionMapper.get(entityId)
        position.set(x, y, plane)

        val face = facingMapper.get(entityId)
        face.x = direction.deltaX
        face.y = direction.deltaY
        return entityId
    }
}

fun npcSpawn(id: Int, x: Int, y: Int, plane: Int = 0, direction: Direction = Direction.NONE) = init {
    game.getSystem(EventSystem::class).dispatch(CreateNpc(id, x, y, plane, direction))
}