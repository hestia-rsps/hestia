package worlds.gregs.hestia.core.plugins.dialogue.components

import worlds.gregs.hestia.game.queue.QueueContext

/**
 * Stores the active [Dialogue] in a queue
 */
class DialogueContext : QueueContext {
    var dialogue: Dialogue? = null
}

