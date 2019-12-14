package worlds.gregs.hestia.core.display.dialogue.api

/**
 * For sharing dialogues lines
 */
abstract class LinesDialogue : Dialogue {
    abstract val title: String?
    abstract val lines: List<String>
}