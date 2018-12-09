package worlds.gregs.hestia.game.plugins.entity.systems

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.events.*
import worlds.gregs.hestia.game.plugins.core.components.entity.Size
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.*
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Facing
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Watch
import worlds.gregs.hestia.game.plugins.movement.components.calc.Interact
import worlds.gregs.hestia.game.plugins.movement.components.calc.Navigate
import worlds.gregs.hestia.game.plugins.movement.components.calc.Path
import worlds.gregs.hestia.game.plugins.movement.components.types.MoveStep
import worlds.gregs.hestia.game.region.FixedTileStrategy
import worlds.gregs.hestia.game.update.Marker
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem

class Controls
/**
 * Entity controls
 * Extensions of [Entity]
 */
fun Entity.batchAnim() {
    edit().add(BatchAnimations())
}

/**
 * Overlays a colour on the entity
 */
fun Entity.colour(hue: Int, sat: Int, lum: Int, multi: Int, duration: Int, delay: Int = 0) {
    edit().add(ColourOverlay(delay, duration, hue or (sat shl 8) or (lum shl 16) or (multi shl 24)))
}

/**
 * Force chat
 */
fun Entity.force(message: String) {
    edit().add(ForceChat(message))
}

/**
 * Delayed force single movement
 */
fun Entity.force(initialDelay: Int, position: Position, duration: Int, direction: Int) {
    val current = getComponent(Position::class) ?: return
    force(current, initialDelay, direction, position, duration)
}

/**
 * Single or double force movement
 */
fun Entity.force(position: Position, delay: Int, direction: Int, secondPosition: Position? = null, secondDelay: Int = 0, move: Boolean = true) {
    if (delay <= 0 || secondDelay < 0) {
        throw IllegalArgumentException("Force movement delay must be positive.")
    }
    if (secondPosition != null && delay >= secondDelay) {
        throw IllegalArgumentException("First delay must be less than the second")
    }
    //Add force movement
    val movement = ForceMovement(position, delay, secondPosition, secondDelay, direction)
    edit().add(movement)

    //Moves player to finishing position after force is complete
    if (move) {
        world.schedule(Math.max(delay, secondDelay) - 1, 0) {
            move(secondPosition ?: position)
        }
    }
}

/**
 * Time bar
 */
fun Entity.time(full: Boolean = false, exponent: Int = 0, delay: Int = 0, increment: Int = 1) {
    edit().add(TimeBar(full, exponent, delay, increment))
}

/**
 * Mob transform
 */
fun Entity.transform(id: Int) {
    edit().add(Transform(id))
    updateAppearance()//Player only
}

/**
 * Turn to face delta direction
 */
fun Entity.turn(deltaX: Int, deltaY: Int) {
    getComponent(Face::class)?.apply {
        x = deltaX
        y = deltaY
    }
    edit().add(Facing())
}

/**
 * Face position
 */
fun Entity.face(x: Int, y: Int) {
    val size = getComponent(Size::class)
    val position = getComponent(Position::class)!!
    val deltaX = x - Facing.getFaceX(position, size?.sizeX ?: 1)
    val deltaY = y - Facing.getFaceY(position, size?.sizeY ?: 1)
    turn(deltaX, deltaY)
}

/**
 * Watch another entity
 */
fun Entity.watch(entityId: Int) {
    edit().add(Watch(entityId))
}

/**
 * Walk within interact distance
 */
fun Entity.interact(x: Int, y: Int, max: Int = -1, size: Int = 1, check: Boolean = true, calculate: Boolean = true) {
    edit().add(Interact(x, y, max, size, check, calculate))
}

/**
 * Navigate to position
 */
fun Entity.navigate(x: Int, y: Int, max: Int = -1, check: Boolean = true) {
    edit().add(Navigate(x, y, max, check))
}

/**
 * Path find to position
 */
fun Entity.path(x: Int, y: Int, max: Int = -1, check: Boolean = true) {
    edit().add(Path(x, y, FixedTileStrategy(x, y), max, check))
}

/**
 * Instant move to position
 */
fun Entity.move(position: Position) {
    move(position.x, position.y, position.plane)
}

/**
 * Instant move to position
 */
fun Entity.move(x: Int, y: Int, plane: Int = 0) {
    edit().add(MoveStep(x, y, plane))
}

/**
 *
 */
fun Entity.animate(id: Int, speed: Int = 0) {
    world.getSystem(EventSystem::class).dispatch(Animate(this.id, id, speed))
}

fun Entity.graphic(id: Int, delay: Int = 0, height: Int = 0, rotation: Int = 0, refresh: Boolean = false) {
    world.getSystem(EventSystem::class).dispatch(Graphic(this.id, id, delay, height, rotation, refresh))
}

fun Entity.hit(amount: Int, mark: Int = if (amount == 0) Marker.MISSED else Marker.REGULAR, delay: Int = 0, critical: Boolean = false, source: Int = -1, soak: Int = -1) {
    world.getSystem(EventSystem::class).dispatch(Hit(this.id, amount, mark, delay, critical, source, soak))
}

fun Entity.updateAppearance() {
    world.getSystem(EventSystem::class).dispatch(UpdateAppearance(this.id))
}

fun Entity.updateMapRegion(local: Boolean, forceRefresh: Boolean = false) {
    world.getSystem(EventSystem::class).dispatch(UpdateMapRegion(this.id, local, forceRefresh))
}