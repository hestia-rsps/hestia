package worlds.gregs.hestia.core.display.dialogue.model

data class DialogueBuilder(val target: Int, var animation: Int = Expression.Talking, var title: String? = null, var message: String = "", var large: Boolean = false, var `continue`: Boolean = true)