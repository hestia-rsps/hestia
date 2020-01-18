package worlds.gregs.hestia.core.display.dialogue.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Event notifying the continuation of a dialogue chain
 * @param id The interface id
 * @param option The selection option
 * @param component The selected component
 */
data class ContinueDialogue(val id: Int, val option: Int, val component: Int) : EntityAction()