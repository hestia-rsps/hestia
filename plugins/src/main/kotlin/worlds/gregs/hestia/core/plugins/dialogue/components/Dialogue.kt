package worlds.gregs.hestia.core.plugins.dialogue.components

import worlds.gregs.hestia.game.queue.QueueScope

/**
 * A suspendable queue of [Dialogue]'s
 * The current active [Dialogue] is found inside [DialogueContext]
 */
typealias DialogueQueue = suspend QueueScope<DialogueContext>.() -> Unit

/**
 * Dialogue stores data to be sent & retrieved from the client during a [DialogueQueue]
 */
interface Dialogue
