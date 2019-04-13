package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import com.artemis.annotations.Wire
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.Configs.LEVEL_UP_DETAILS
import worlds.gregs.hestia.game.Configs.SKILL_MENU
import worlds.gregs.hestia.game.Configs.SKILL_STAT_FLASH
import worlds.gregs.hestia.game.entity.player.Skill
import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.StatsTab
import worlds.gregs.hestia.game.plugins.widget.components.screen.SkillLevelDetails
import worlds.gregs.hestia.game.plugins.widget.components.screen.SkillMenu
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.game.plugins.widget.systems.screen.SkillLevelDetailsSystem
import worlds.gregs.hestia.game.plugins.widget.systems.screen.SkillMenuSystem
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel
import worlds.gregs.hestia.services.send
import kotlin.math.pow

@Wire(failOnNull = false, injectInherited = true)
class StatsTabSystem : BaseFrame(StatsTab::class) {

    private var ui: UserInterface? = null

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)

        val total = flash.filter { false /* has leveled up */ }.map { 2.0.pow(it.ordinal) }.sum().toInt()
        es.send(entityId, Config(SKILL_STAT_FLASH, total))

        Skill.values().forEach {
            es.send(entityId, SkillLevel(it.ordinal, 99, 14000000))
        }
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            11, 19, 28, 36, 44, 52, 60, 68, 76, 84, 93, 101, 110, 118, 126, 134, 142, 150, 158, 165, 172, 179, 186, 193, 200 -> {
                val index = when {
                    componentId >= 165 -> 19 + (componentId - 165) / 7
                    componentId >= 110 -> 12 + (componentId - 110) / 8
                    componentId == 101 -> 11
                    componentId == 93 -> 10
                    componentId >= 28 -> 2 + (componentId - 28) / 8
                    componentId == 19 -> 1
                    else -> 0
                }
                val skill = stats[index]
                val menuIndex = menu.indexOf(skill) + 1

                val leveledUp = false
                val extra = 0
                /*
                    2 - combat level milestone
                    4 - total level milestone
                    6 - total level & combat level milestones
                 */
                val config = if(leveledUp) menuIndex * 8 + extra else menuIndex

                es.send(entityId, Config(if(leveledUp) LEVEL_UP_DETAILS else SKILL_MENU, config))
                if(ui?.contains(entityId, if(leveledUp) SkillLevelDetailsSystem::class else SkillMenuSystem::class) != true) {
                    ui?.open(entityId, if(leveledUp) SkillLevelDetails() else SkillMenu())
                }

                if(leveledUp) {
                    //TODO Replace with SKILL_STAT_FLASH config refresh once level up system is added
                    //Disable flash
                    es.send(entityId, ConfigFile(if(skill == Skill.DUNGEONEERING) 7756 else 4731 + menuIndex, 0))
                }
            }
        }
    }

    companion object {
        private const val TAB_ID = 320
        private const val RESIZABLE_INDEX = 92
        private const val FIXED_INDEX = 206

        private val stats = listOf(Skill.STRENGTH, Skill.AGILITY, Skill.DEFENCE, Skill.HERBLORE, Skill.FISHING, Skill.RANGE, Skill.THIEVING, Skill.COOKING, Skill.PRAYER, Skill.CRAFTING, Skill.MAGIC, Skill.FLETCHING, Skill.RUNECRAFTING,
                Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER, Skill.SUMMONING, Skill.DUNGEONEERING, Skill.WOODCUTTING, Skill.FIREMAKING, Skill.SMITHING, Skill.MINING, Skill.CONSTITUTION, Skill.ATTACK)
        private val menu = listOf(Skill.ATTACK, Skill.STRENGTH, Skill.RANGE, Skill.MAGIC, Skill.DEFENCE, Skill.CONSTITUTION, Skill.PRAYER, Skill.AGILITY, Skill.HERBLORE, Skill.THIEVING, Skill.CRAFTING, Skill.RUNECRAFTING,
                Skill.MINING, Skill.SMITHING, Skill.FISHING, Skill.COOKING, Skill.FIREMAKING, Skill.WOODCUTTING, Skill.FLETCHING, Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER, Skill.SUMMONING, Skill.DUNGEONEERING)
        private val flash = listOf(Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGE, Skill.PRAYER, Skill.MAGIC, Skill.CONSTITUTION, Skill.AGILITY, Skill.HERBLORE, Skill.THIEVING, Skill.CRAFTING, Skill.FLETCHING, Skill.MINING,
                Skill.SMITHING, Skill.FISHING, Skill.COOKING, Skill.FIREMAKING, Skill.WOODCUTTING, Skill.RUNECRAFTING, Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER, Skill.SUMMONING, Skill.DUNGEONEERING)

    }
}