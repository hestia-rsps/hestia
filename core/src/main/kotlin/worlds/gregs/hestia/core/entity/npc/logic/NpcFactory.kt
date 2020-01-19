package worlds.gregs.hestia.core.entity.npc.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.artemis.add
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.npc.api.Npc
import worlds.gregs.hestia.core.world.movement.api.Mobile

class NpcFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Npc::class, ClientIndex::class, Renderable::class, Position::class, Type::class, Mobile::class, Facing::class)
    }
}