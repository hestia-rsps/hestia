package worlds.gregs.hestia.core.script.dsl.task

import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.task.api.*
import worlds.gregs.hestia.core.task.logic.systems.awaitDistance
import worlds.gregs.hestia.core.task.logic.systems.awaitRoute
import worlds.gregs.hestia.core.task.logic.systems.wait
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem
import worlds.gregs.hestia.core.world.movement.model.components.calc.Follow
import worlds.gregs.hestia.core.world.movement.model.components.calc.Route
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

/*
    Interaction
 */

data class DistanceBuilder(val entity: Int, var distance: Int = 1)

data class InteractBuilder(val distance: DistanceBuilder, var target: Int = -1)


/**
 * Checks static entity is valid, creates route, waits for movement and returns if reached entity
 */
suspend fun Task.interact(builder: InteractBuilder) {
    val (distance, target) = builder
    val (entity, range) = distance
    onCancel {
        entity face target
        entity message "You can't reach that."
    }

    val position = entity get Position::class
    val targetPosition = target get Position::class

    //Check valid entity
    if (!world.entityManager.isActive(target) || position == null || targetPosition == null || targetPosition.plane != position.plane) {
        return cancel(TaskCancellation.OutOfReach)
    }

    val result: Boolean

    //If range check, then follow (mob/player)
    if (range > 0) {
        //Start following
        val follow = entity create Follow::class
        follow.entity = target

        //Wait till within distance
        result = awaitDistance(target, range)
        //Cancel target
        follow.entity = -1
    } else {
        //Start route
        val route = entity create Route::class
        route.entityId = target
        route.alternative = true

        val actualRoute = awaitRoute()
        wait(1) //The system processes before the client has updated, so we wait 1 tick allowing the client to catch up.
        result = canInteract(actualRoute, position, targetPosition, target)
    }

    //Cancel if not possible
    if (!result) {
        cancel(TaskCancellation.OutOfReach)
    }
}

private fun Task.canInteract(route: worlds.gregs.hestia.core.task.logic.systems.Route, position: Position, targetPosition: Position, targetEntityId: Int): Boolean {
    if (route.steps < 0) {
        return false
    }
    val sizeX: Int
    val sizeY: Int

    val gameObject = getMapper(GameObject::class).get(targetEntityId)
    if (gameObject != null) {
        val definitions = getSystem(ObjectDefinitionSystem::class)
        val definition = definitions.get(gameObject.id)
        sizeX = definition.sizeX
        sizeY = definition.sizeY
    } else {
        val sizeMapper = getMapper(Size::class)
        sizeX = sizeMapper.width(targetEntityId)
        sizeY = sizeMapper.height(targetEntityId)
    }

    return !route.alternative || StepBesideSystem.isNear(position.x, position.y, targetPosition.x, targetPosition.y, sizeX, sizeY, true)
}
