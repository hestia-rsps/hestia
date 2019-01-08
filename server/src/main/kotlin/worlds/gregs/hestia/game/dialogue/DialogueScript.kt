package worlds.gregs.hestia.game.dialogue


/**
 * Annotation for packet opcode(s)
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class DialogueName(vararg val names: String)

interface DialogueScript {
    val dialogue: Dialogues.() -> Unit
}