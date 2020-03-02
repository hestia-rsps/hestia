
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.ACTIVE_CURSES
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.ACTIVE_PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.QUICK_CURSES
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.QUICK_PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.SELECTING_QUICK_PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.TEMP_QUICK_PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.USING_QUICK_PRAYERS
import worlds.gregs.hestia.content.display.Tabs.PrayerListTab
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.client.model.events.DisconnectClient
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.variable.model.events.AddVariable
import worlds.gregs.hestia.core.display.variable.model.events.RemoveVariable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.events.ToggleVariable
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVariable
import worlds.gregs.hestia.core.entity.entity.model.components.Blackboard
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.game.plugin.init
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings
import worlds.gregs.hestia.service.cache.config.systems.StrutDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.EnumDefinitionSystem

lateinit var enums: EnumDefinitionSystem
lateinit var struts: StrutDefinitionSystem
lateinit var variables: Variables

val nameRegex = "<br>(.*?)<br>".toRegex()
val logger = LoggerFactory.getLogger(this::class.java)!!
val prayerEnumId = 2279
val curseEnumId = 863

lateinit var prayerGroups: Set<Set<String>>
lateinit var cursesGroups: Set<Set<String>>

fun loadPrayerNames(enumId: Int): List<String> {
    val list = mutableListOf<Pair<Int, String>>()
    enums.get(enumId).map?.forEach { (_, value) ->
        val strut = struts.get(value as Int).params
        val name = getPrayerName(strut)!!
        list.add(value to name)
    }
    list.sortBy { it.first }
    return list.map { it.second }
}

init {
    val prayerNames = loadPrayerNames(prayerEnumId)
    BitwiseVariable(1395, Variable.Type.VARP, values = prayerNames).register(ACTIVE_PRAYERS)
    BitwiseVariable(1397, Variable.Type.VARP, true, values = prayerNames).register(QUICK_PRAYERS)
    var groups = setOf(
            intArrayOf(9, 26, 0, 25, 3),
            intArrayOf(1, 26, 25, 10, 4),
            intArrayOf(5, 11, 26, 2, 25),
            intArrayOf(29, 18, 20, 22),
            intArrayOf(19, 23, 21, 28),
            intArrayOf(27, 7),
            intArrayOf(17, 13, 8, 14, 16, 12, 15),
            intArrayOf(16, 15, 17, 24)
    )
    prayerGroups = groups.map { it.map { index -> prayerNames[index] }.toHashSet() }.toSet()

    val curseNames = loadPrayerNames(curseEnumId)
    BitwiseVariable(1582, Variable.Type.VARP, values = curseNames).register(ACTIVE_CURSES)
    BitwiseVariable(1587, Variable.Type.VARP, true, values = curseNames).register(QUICK_CURSES)
    groups = setOf(
            intArrayOf(17, 18),
            intArrayOf(6, 17, 18),
            intArrayOf(14, 19),
            intArrayOf(1, 10, 19),
            intArrayOf(7, 8, 9, 17, 18),
            intArrayOf(3, 12, 19),
            intArrayOf(2, 11, 19),
            intArrayOf(13, 19),
            intArrayOf(4, 16, 19)
    )
    cursesGroups = groups.map { it.map { index -> curseNames[index] }.toHashSet() }.toSet()
    println(cursesGroups)
}

// Prayer activate/deactivate
on<InterfaceInteraction> {
    where { id == PrayerList && component == 8 }
    then {
        togglePrayer(second, if(isCurses()) ACTIVE_CURSES else ACTIVE_PRAYERS)
    }
}

// Quick prayer activate/deactivate
on<InterfaceInteraction> {
    where { id == PrayerList && component == 42 }
    then {
        togglePrayer(second, if(isCurses()) QUICK_CURSES else QUICK_PRAYERS)
    }
}

fun InterfaceInteraction.togglePrayer(prayerIndex: Int, listKey: String) {
    val curses = isCurses()
    val params = getPrayerParameters(prayerIndex, if(curses) curseEnumId else prayerEnumId)
    val requiredLevel = params?.get(737) as? Int ?: 0
    // TODO level check
    val name = getPrayerName(params) ?: return logger.warn("Unable to find prayer button $id $component $second")
    val activated = variables.has(entity, listKey, name)
    if (activated) {
        entity perform RemoveVariable(listKey, name)
    } else {
        for (group in if(curses) cursesGroups else prayerGroups) {
            if (group.contains(name)) {
                group.forEach {
                    entity perform RemoveVariable(listKey, it, false)
                }
            }
        }
        entity perform AddVariable(listKey, name)
    }
    //TODO update stats
    //TODO impl appearance head icons
    entity perform UpdateAppearance()
}

// Select quick prayers
on<InterfaceInteraction> {
    where { id == PrayerOrb && component == 1 && option == 2 }
    then {
        entity perform ToggleVariable(SELECTING_QUICK_PRAYERS)
        val selecting = variables.get(entity, SELECTING_QUICK_PRAYERS, false)
        val blackboard = entity get Blackboard::class
        val curses = isCurses()
        if (selecting) {
            entity perform SetVariable("tab", PrayerListTab)
            blackboard[TEMP_QUICK_PRAYERS] = variables.get(entity, if(curses) QUICK_CURSES else QUICK_PRAYERS, 0)
        } else if (blackboard.has(TEMP_QUICK_PRAYERS)) {
            cancelQuickPrayers(curses)
        }
        entity send InterfaceSettings(PrayerList, if (selecting) 42 else 8, 0, 29, 2)
    }
}

// Toggle quick prayers
on<InterfaceInteraction> {
    where { id == PrayerOrb && component == 1 && option == 1 }
    then {
        entity perform ToggleVariable(USING_QUICK_PRAYERS)
        val active = variables.get(entity, USING_QUICK_PRAYERS, false)
        val curses = isCurses()
        if (active) {
            val quickPrayers = variables.get(entity, if(curses) QUICK_CURSES else QUICK_PRAYERS, 0)
            if (quickPrayers > 0) {
                entity perform SetVariable(if(curses) ACTIVE_CURSES else ACTIVE_PRAYERS, quickPrayers)
            }
        } else {
            entity perform SetVariable(if(curses) ACTIVE_CURSES else ACTIVE_PRAYERS, 0)
        }
    }
}

// Confirm quick prayers
on<InterfaceInteraction> {
    where { id == PrayerList && component == 43 }
    then {
        entity perform SetVariable(SELECTING_QUICK_PRAYERS, false)
        val blackboard = entity get Blackboard::class
        blackboard.remove(TEMP_QUICK_PRAYERS)
    }
}

// Cancel quick prayers
on<DisconnectClient> {
    where { entity has Blackboard::class && entity.get(Blackboard::class).has(TEMP_QUICK_PRAYERS) }
    then {
        cancelQuickPrayers(isCurses())
    }
}

fun EntityAction.cancelQuickPrayers(curses: Boolean) {
    val blackboard = entity get Blackboard::class
    entity perform SetVariable(if(curses) QUICK_CURSES else QUICK_PRAYERS, blackboard.get(TEMP_QUICK_PRAYERS, 0))
    blackboard.remove(TEMP_QUICK_PRAYERS)
}

fun getPrayerParameters(index: Int, enumId: Int): HashMap<Long, Any>? {
    val enum = enums.get(enumId).map!!
    return struts.get(enum[index] as Int).params
}

fun getPrayerName(params: HashMap<Long, Any>?): String? {
    val description = params?.getOrDefault(734, null) as? String ?: return null
    return nameRegex.find(description)?.groupValues?.lastOrNull()
}

fun EntityAction.isCurses(): Boolean = variables.get<String>(entity, PRAYERS) == "curses"
