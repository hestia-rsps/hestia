package worlds.gregs.hestia.core.plugins.dialogue.systems.types

import worlds.gregs.hestia.core.plugins.dialogue.components.Dialogue

data class StringEntryDialogue(val title: String?) : Dialogue {

    var entry: String? = null

//    override fun met(): Boolean = entry != null
}