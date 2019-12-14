package worlds.gregs.hestia.core.task.api

import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueSystem
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.display.widget.model.events.CloseDialogue
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size



fun Task.watch(entityId: Int, targetId: Int) {
    val watch = entityId create Watch::class
    watch.entity = targetId
}

/**
 * Face position
 */
fun Task.face(entityId: Int) {
    val position = (entityId get Position::class)!!
    face(position.x, position.y)
}

/**
 * Face position
 */
fun Task.face(x: Int, y: Int) {
    val sizeMapper = getMapper(Size::class)
    val positionMapper = getMapper(Position::class)

    val size = sizeMapper.get(entity)
    val position = positionMapper.get(entity)

    val deltaX = x - Facing.getFaceX(position, size?.sizeX ?: 1)
    val deltaY = y - Facing.getFaceY(position, size?.sizeY ?: 1)
    turn(deltaX, deltaY)
}

/**
 * Turn to face delta direction
 */
fun Task.turn(deltaX: Int, deltaY: Int) {
    val facingMapper = getMapper(Facing::class)
    val faceMapper = getMapper(Face::class)
    val face = faceMapper.create(entity)
    face.x = deltaX
    face.y = deltaY
    facingMapper.create(entity)
}

fun Task.startDialogue(name: String) {
    getSystem(DialogueSystem::class).startDialogue(entity, name)
}

fun Task.dispatch(event: Event) {
    val es = world.getSystem(EventSystem::class)
    es.dispatch(event)
}

fun Task.closeDialogue() = dispatch(CloseDialogue(entity))