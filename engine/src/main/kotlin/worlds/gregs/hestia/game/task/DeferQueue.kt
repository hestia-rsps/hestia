package worlds.gregs.hestia.game.task

/**
 * A suspendable queue
 */
typealias DeferQueue = suspend TaskScope.() -> Unit