package worlds.gregs.hestia.core.display.dialogue.api

import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.game.task.DeferralType

/**
 * Dialogue stores data to be sent & retrieved from the client during a [TaskQueue]
 */
interface Dialogue : DeferralType