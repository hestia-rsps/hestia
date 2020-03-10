package worlds.gregs.hestia.core.entity.item.floor.logic.creation

import com.artemis.Archetype
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.SpawnPoint
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItemSpawn
import worlds.gregs.hestia.game
import worlds.gregs.hestia.game.plugin.init

@Wire(failOnNull = false)
class FloorItemSpawnCreation : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var amountMapper: ComponentMapper<Amount>
    private lateinit var spawnPointMapper: ComponentMapper<SpawnPoint>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = FloorItemSpawnFactory().getBuilder().build(world)
    }

    @Subscribe
    fun create(event: CreateFloorItemSpawn): Int {
        return create(event.id, event.amount, event.x, event.y, event.plane, event.delay)
    }

    private fun create(id: Int, amount: Int, x: Int, y: Int, plane: Int, delay: Int): Int {
        val entityId = world.create(archetype)

        val spawnPoint = spawnPointMapper.get(entityId)
        spawnPoint.delay = delay
        spawnPoint.delayTime = delay

        val type = typeMapper.get(entityId)
        type.id = id

        val amt = amountMapper.get(entityId)
        amt.amount = amount

        val position = positionMapper.get(entityId)
        position.set(x, y, plane)

        return entityId
    }
}

fun itemSpawn(id: Int, amount: Int, delay: Int, x: Int, y: Int, plane: Int = 0) = init {
    game.getSystem(EventSystem::class).dispatch(CreateFloorItemSpawn(id, amount, x, y, plane, delay))
}