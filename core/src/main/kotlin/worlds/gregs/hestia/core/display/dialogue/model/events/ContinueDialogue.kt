package worlds.gregs.hestia.core.display.dialogue.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Event notifying the continuation of a dialogue chain
 * @param window The window id
 * @param option The selection option
 * @param widget The selected widget
 */
data class ContinueDialogue(val window: Int, val option: Int, val widget: Int) : EntityAction()