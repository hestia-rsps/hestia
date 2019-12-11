package worlds.gregs.hestia.core.plugins.dialogue.components

/**
 * For sharing dialogues lines
 */
abstract class LinesDialogue : Dialogue {
    abstract val title: String?
    abstract val lines: List<String>
}