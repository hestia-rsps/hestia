package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.PlayerOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.world.movement.model.events.Follow

on(PlayerOption, "Follow") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity perform Follow(target)
    }
    then(EntityActions::task)
}