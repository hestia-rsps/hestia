package worlds.gregs.hestia.core.display.dialogue.logic.systems

interface LinesDialogueTest {

    fun lines(count: Int) = (0 until count).joinToString(separator = "\n") { "Line ${it + 1}" }

}