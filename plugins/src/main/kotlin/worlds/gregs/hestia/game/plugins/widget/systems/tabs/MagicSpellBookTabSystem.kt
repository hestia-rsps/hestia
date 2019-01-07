package worlds.gregs.hestia.game.plugins.widget.systems.tabs

import worlds.gregs.hestia.game.plugins.widget.components.tabs.LogoutTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseTab

class MagicSpellBookTabSystem : BaseTab(LogoutTab::class) {

    override var id = MODERN_SPELLBOOK

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun getId(entityId: Int): Int {
        //TODO spell books
        return MODERN_SPELLBOOK
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
        when(componentId) {
            2 -> {//Defensive casting
            }
            7 -> {//Combat spells
            }
            9 -> {//Teleport spells
            }
            11 -> {//Misc spells
            }
            13 -> {//Skill spells
            }
            15 -> {//Sort by level order
            }
            16 -> {//Sort by combat first
            }
            17 -> {//Sort by teleports first
            }
        }
    }

    companion object {
        private const val LUNAR_SPELLBOOK = 430
        private const val ANCIENT_SPELLBOOK = 193
        private const val MODERN_SPELLBOOK = 192
        private const val DUNGEONEERING_SPELLBOOK = 950

        private const val TAB_ID = 192
        private const val RESIZABLE_INDEX = 97
        private const val FIXED_INDEX = 211
    }
}