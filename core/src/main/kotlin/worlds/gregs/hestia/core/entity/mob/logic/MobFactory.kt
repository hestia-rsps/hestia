package worlds.gregs.hestia.core.entity.mob.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.artemis.add

class MobFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Mob::class, ClientIndex::class, Renderable::class, Position::class, Type::class, Mobile::class, Facing::class)
    }
}