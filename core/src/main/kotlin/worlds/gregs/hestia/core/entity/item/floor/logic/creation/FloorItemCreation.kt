package worlds.gregs.hestia.core.entity.item.floor.logic.creation

import com.artemis.Archetype
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem

@Wire(failOnNull = false)
class FloorItemCreation : PassiveSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var privateMapper: ComponentMapper<Private>
    private lateinit var publicMapper: ComponentMapper<Public>
    private lateinit var amountMapper: ComponentMapper<Amount>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = FloorItemFactory().getBuilder().build(world)
    }

    @Subscribe
    fun create(event: CreateFloorItem): Int {
        val entityId = world.create(archetype)

        val type = typeMapper.get(entityId)
        type.id = event.type

        val amount = amountMapper.get(entityId)
        amount.amount = event.amount

        val position = positionMapper.get(entityId)
        position.set(event.x, event.y, event.plane)

        if(event.publicTime != null) {
            val public = publicMapper.create(entityId)
            public.owner = event.clientIndex ?: -1
            public.timeout = event.publicTime
        }

        if(event.privateTime != null) {
            val public = privateMapper.create(entityId)
            public.id = event.owner
            public.timeout = event.privateTime
        }

        return entityId
    }
}