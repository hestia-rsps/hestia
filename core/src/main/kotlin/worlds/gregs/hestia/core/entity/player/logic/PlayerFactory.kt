package worlds.gregs.hestia.core.entity.player.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.artemis.add
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.model.components.AppearanceData
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.display.interfaces.model.components.*
import worlds.gregs.hestia.core.display.request.model.components.Assisting
import worlds.gregs.hestia.core.display.request.model.components.ContextMenu
import worlds.gregs.hestia.core.display.request.model.components.RequestList
import worlds.gregs.hestia.core.display.variable.model.VariableStore
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Blackboard
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.container.model.ContainerList
import worlds.gregs.hestia.core.entity.player.model.components.Member
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.collision.model.components.Ghost
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement
import worlds.gregs.hestia.game.entity.Player

class PlayerFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Viewport::class, Mobile::class, LastLoadedRegion::class, Shift::class, Facing::class, Ghost::class, GameFrame::class,
                TaskQueue::class, ContainerList::class, Member::class, InterfaceRelationships::class, ContextMenu::class, RequestList::class, Movement::class, Assisting::class, VariableStore::class, Blackboard::class)
    }
}