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
import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.display.client.model.events.DisconnectClient
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.variable.model.events.AddVariable
import worlds.gregs.hestia.core.display.variable.model.events.RemoveVariable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.events.ToggleVariable
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVariable
import worlds.gregs.hestia.core.entity.entity.model.components.Blackboard
import worlds.gregs.hestia.core.entity.player.model.components.update.HeadIcon
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.core.action.logic.systems.on
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
}

// Prayer activate/deactivate
on(InterfaceOption, PrayerList, components = *intArrayOf(8)) { _, _, second, _ ->
    val curses = isCurses()
    val key = if (curses) ACTIVE_CURSES else ACTIVE_PRAYERS
    togglePrayer(second, key)
    update(curses, key)
}

// Quick prayer activate/deactivate
on(InterfaceOption, PrayerList, components = *intArrayOf(42)) { _, _, second, _ ->
    togglePrayer(second, if (isCurses()) QUICK_CURSES else QUICK_PRAYERS)
}

fun EntityActions.togglePrayer(prayerIndex: Int, listKey: String) {
    val curses = isCurses()
    val params = getPrayerParameters(prayerIndex, if (curses) curseEnumId else prayerEnumId)
    val name = getPrayerName(params) ?: return logger.warn("Unable to find prayer button $prayerIndex $listKey $params")
    val activated = variables.has(entity, listKey, name)
    if (activated) {
        entity perform RemoveVariable(listKey, name)
    } else {
        val requiredLevel = params?.get(737) as? Int ?: 0
        // TODO level check
        for (group in if (curses) cursesGroups else prayerGroups) {
            if (group.contains(name)) {
                group.forEach {
                    entity perform RemoveVariable(listKey, it, false)
                }
            }
        }
        entity perform AddVariable(listKey, name)
    }
}

fun EntityActions.update(curses: Boolean, listKey: String) {
    //TODO update stats
    val changed = if (curses) {
        setCurseIcon(listKey)
    } else {
        setPrayerIcon(listKey)
    }
    if (changed) {
        entity perform UpdateAppearance()
    }
}

fun EntityActions.setCurseIcon(listKey: String): Boolean {
    val headIcon = entity create HeadIcon::class
    var value = -1
    when {
        variables.has(entity, listKey, "Wrath") -> value = 19
        variables.has(entity, listKey, "Soul Split") -> value = 20
        else -> {
            if (variables.has(entity, listKey, "Deflect Summoning")) {
                value += 4
            }

            value += when {
                variables.has(entity, listKey, "Deflect Magic") -> if (value > -1) 3 else 2
                variables.has(entity, listKey, "Deflect Missiles") -> if (value > -1) 2 else 3
                variables.has(entity, listKey, "Deflect Melee") -> 1
                else -> 0
            }
            if (value > -1) {
                value += 12
            }
        }
    }
    if (headIcon.headIcon != value) {
        headIcon.headIcon = value
        return true
    }
    return false
}

fun EntityActions.setPrayerIcon(listKey: String): Boolean {
    val headIcon = entity create HeadIcon::class
    var value = -1
    when {
        variables.has(entity, listKey, "Retribution") -> value = 3
        variables.has(entity, listKey, "Redemption") -> value = 5
        variables.has(entity, listKey, "Smite") -> value = 4
        else -> {
            if (variables.has(entity, listKey, "Protect from Summoning")) {
                value += 8
            }

            value += when {
                variables.has(entity, listKey, "Protect from Magic") -> 3
                variables.has(entity, listKey, "Protect from Missiles") -> 2
                variables.has(entity, listKey, "Protect from Melee") -> 1
                else -> 0
            }
        }
    }
    if (headIcon.headIcon != value) {
        headIcon.headIcon = value
        return true
    }
    return false
}

// Select quick prayers
on(InterfaceOption, "Select Quick Prayers", id = PrayerOrb) { _, _, _, _ ->
    entity perform ToggleVariable(SELECTING_QUICK_PRAYERS)
    val selecting = variables.get(entity, SELECTING_QUICK_PRAYERS, false)
    val blackboard = entity get Blackboard::class
    val curses = isCurses()
    if (selecting) {
        entity perform SetVariable("tab", PrayerListTab)
        blackboard[TEMP_QUICK_PRAYERS] = variables.get(entity, if (curses) QUICK_CURSES else QUICK_PRAYERS, 0)
    } else if (blackboard.has(TEMP_QUICK_PRAYERS)) {
        cancelQuickPrayers(curses)
    }
    entity send InterfaceSettings(PrayerList, if (selecting) 42 else 8, 0, 29, 2)
}

// Toggle quick prayers
on(InterfaceOption, "Turn Quick Prayers On", id = PrayerOrb) { _, _, _, _ ->
    entity perform ToggleVariable(USING_QUICK_PRAYERS)
    val active = variables.get(entity, USING_QUICK_PRAYERS, false)
    val curses = isCurses()
    val activePrayers = if (curses) ACTIVE_CURSES else ACTIVE_PRAYERS
    if (active) {
        //If selecting others we want to toggle the old ones until otherwise confirmed
        val blackboard = entity get Blackboard::class
        val quickPrayers = blackboard.getOrNull(TEMP_QUICK_PRAYERS)
                ?: variables.get(entity, if (curses) QUICK_CURSES else QUICK_PRAYERS, 0)
        if (quickPrayers > 0) {
            entity perform SetVariable(activePrayers, quickPrayers)
        }
    } else {
        entity perform SetVariable(activePrayers, 0)
    }
    update(curses, activePrayers)
}

// Confirm quick prayers
on(InterfaceOption, "Confirm Selection", id = PrayerList) { _, _, _, _ ->
    entity perform SetVariable(SELECTING_QUICK_PRAYERS, false)
    val blackboard = entity get Blackboard::class
    blackboard.remove(TEMP_QUICK_PRAYERS)
}

// Cancel quick prayers
on<DisconnectClient> {
    where { entity has Blackboard::class && entity.get(Blackboard::class).has(TEMP_QUICK_PRAYERS) }
    then {
        cancelQuickPrayers(isCurses())
    }
}

fun EntityActions.cancelQuickPrayers(curses: Boolean) {
    val blackboard = entity get Blackboard::class
    entity perform SetVariable(if (curses) QUICK_CURSES else QUICK_PRAYERS, blackboard.get(TEMP_QUICK_PRAYERS, 0))
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

fun EntityActions.isCurses(): Boolean = variables.get<String>(entity, PRAYERS) == "curses"
