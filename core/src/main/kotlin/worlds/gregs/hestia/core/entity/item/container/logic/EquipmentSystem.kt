package worlds.gregs.hestia.core.entity.item.container.logic

import arrow.core.andThen
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.entity.item.container.api.Composition
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.equipSlots
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import java.io.DataInputStream
import java.io.File
import kotlin.system.measureTimeMillis

class EquipmentSystem : PassiveSystem() {

    private lateinit var definitions: ItemDefinitionSystem

    fun getEquipId(item: Item) = getEquipId(item.type)
    fun getEquipSlot(item: Item) = getEquipSlot(item.type)
    fun getEquipType(item: Item) = getEquipType(item.type)

    fun getEquipId(type: Int) = equipIds.getOrDefault(type, -1)
    fun getEquipSlot(type: Int) = equipSlots.getOrDefault(type, -1)
    fun getEquipType(type: Int) = equipTypes.getOrDefault(type, -1)

    override fun initialize() {
        var equipId = 0
        val itemCount = definitions.reader.size
        repeat(itemCount) { id ->
            val def = definitions.get(id)
            if(def.primaryMaleModel >= 0 || def.primaryFemaleModel >= 0) {
                equipIds[id] = equipId++
            }
        }

        var file = File("./data/equipmentSlots.dat")
        var stream = DataInputStream(file.inputStream())
        var time = measureTimeMillis {
            while(stream.available() > 0) {
                equipSlots[stream.readShort().toInt()] = stream.readByte().toInt()
            }
        }
        logger.info("${equipSlots.size} equipment slots loaded in ${time}ms")
        file = File("./data/equipmentTypes.dat")
        stream = DataInputStream(file.inputStream())
        time = measureTimeMillis {
            while(stream.available() > 0) {
                equipTypes[stream.readShort().toInt()] = stream.readByte().toInt()
            }
        }
        logger.info("${equipTypes.size} equipment types loaded in ${time}ms")
    }

    companion object {
        const val SLOT_HAT = 0
        const val SLOT_CAPE = 1
        const val SLOT_AMULET = 2
        const val SLOT_WEAPON = 3
        const val SLOT_CHEST = 4
        const val SLOT_SHIELD = 5
        const val SLOT_LEGS = 7
        const val SLOT_HANDS = 9
        const val SLOT_FEET = 10
        const val SLOT_RING = 12
        const val SLOT_ARROWS = 13
        const val SLOT_AURA = 14

        const val HOODED_CAPE = 0
        const val TWO_HANDED = 5
        const val FULL_BODY = 6
        const val HAIR = 8
        const val MASK = 11

        val equipIds = mutableMapOf<Int, Int>()
        val equipSlots = mutableMapOf<Int, Int>()
        val equipTypes = mutableMapOf<Int, Int>()
        private val logger = LoggerFactory.getLogger(EquipmentSystem::class.java)
    }
}

fun equip(type: Int, amount: Int = 1): Composition = {
    add(type, amount, equipSlots.getOrDefault(type, -1))(it)
}

/**
 * Helper function for easily equipping multiple items
 * @param types List of item types to add
 */
fun equipAll(vararg types: Int) = types.fold(blankComposition) { acc, type -> acc andThen equip(type) }

/**
 * Helper function for easily equipping multiple item amounts
 * @param pairs List of item type & amounts to add
 */
fun equipAll(vararg pairs: Pair<Int, Int>) = pairs.fold(blankComposition) { acc, (type, amount) -> acc andThen equip(type, amount) }
