package worlds.gregs.hestia.core.entity.bot.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.artemis.add
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.model.components.AppearanceData
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.request.model.components.ContextMenu
import worlds.gregs.hestia.core.display.request.model.components.RequestList
import worlds.gregs.hestia.core.display.variable.model.VariableStore
import worlds.gregs.hestia.core.display.interfaces.model.components.InterfaceRelationships
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.container.model.ContainerList
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.collision.model.components.Ghost
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement
import worlds.gregs.hestia.game.entity.Player

class BotFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Mobile::class, Ghost::class, TaskQueue::class, Viewport::class, ContainerList::class, InterfaceRelationships::class,
                ContextMenu::class, RequestList::class, Movement::class, VariableStore::class)
    }
}