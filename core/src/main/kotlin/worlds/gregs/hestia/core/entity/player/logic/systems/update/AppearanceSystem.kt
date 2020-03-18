package worlds.gregs.hestia.core.entity.player.logic.systems.update

import com.artemis.ComponentMapper
import io.netty.buffer.Unpooled
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.cache.crypto.Encryption
import world.gregs.hestia.core.network.packet.PacketWriter
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.update.model.components.*
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.FULL_BODY
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.HAIR
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.HOODED_CAPE
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.MASK
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_AURA
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_CAPE
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_CHEST
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_FEET
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_HANDS
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_HAT
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_LEGS
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_SHIELD
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.player.model.components.update.*
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class AppearanceSystem : SubscriptionSystem(Aspect.all(Player::class)) {

    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var appearanceMapper: ComponentMapper<Appearance>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var playerMapper: ComponentMapper<Player>
    private lateinit var coloursMapper: ComponentMapper<Colours>
    private lateinit var bodyMapper: ComponentMapper<Body>
    private lateinit var titleMapper: ComponentMapper<Title>
    private lateinit var skullMapper: ComponentMapper<Skull>
    private lateinit var headIconMapper: ComponentMapper<HeadIcon>
    private lateinit var hiddenMapper: ComponentMapper<Hidden>
    private lateinit var emoteMapper: ComponentMapper<Emote>
    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>
    private lateinit var summoningCombatLevelMapper: ComponentMapper<SummoningCombatLevel>
    private lateinit var containers: ContainerSystem
    private lateinit var equipment: EquipmentSystem
    private lateinit var definitions: ItemDefinitionSystem

    override fun inserted(entityId: Int) {
        update(entityId)
    }

    @Subscribe
    fun update(event: UpdateAppearance) {
        update(event.entity)
    }

    private fun PacketWriter.writeItem(item: Item) {
        writeShort(0x8000 + equipment.getEquipId(item))
    }

    private fun PacketWriter.writeEmpty() {
        writeByte(0)
    }

    private fun PacketWriter.writeClothes(value: Int) {
        writeShort(0x100 + value)
    }

    private fun update(entityId: Int) {
        if (!playerMapper.has(entityId)) {
            return
        }


        val packet = PacketWriter()

        packet.apply {
            val skillLevel = false
            var flag = 0
//            flag = flag or 0x1//gender
//            flag = flag or 0x2//Has display name
            if (skillLevel) {
                flag = flag or 0x4//Skill level displayed rather than combat
            }
//            flag = flag or (size shl 3 and 0x7)//Size
//            flag = flag and ((1 and 0xf2) shr 6)//Something about trimming title
            writeByte(flag)//Flag
            writeByte(titleMapper.get(entityId)?.title ?: 1)//Title
//            writeString(namePrefixMapper.get(entityId)?.prefix ?: "")//Chat prefix
            writeByte(skullMapper.get(entityId)?.skull ?: -1)//Skull
            writeByte(headIconMapper.get(entityId)?.headIcon ?: -1)//Head icon
            writeByte(hiddenMapper.has(entityId))//Hidden (displays visible to admins)

            if (transformMapper.has(entityId)) {
                val transform = transformMapper.get(entityId)
                writeShort(-1)
                writeShort(transform.npcId)
                writeByte(0)
            } else {
                val equip = containers.getContainer(entityId, ContainerType.EQUIPMENT)
                for (index in 0 until 4) {
                    val item = equip.getOrNull(index)
                    if (item == null) {
                        writeEmpty()
                    } else {
                        writeItem(item)
                    }
                }
                val look = bodyMapper.get(entityId)?.look ?: DEFAULT_LOOK
                val chest = equip.getOrNull(SLOT_CHEST)
                if (chest == null) {
                    writeClothes(look[2])
                } else {
                    writeItem(chest)//Torso
                }

                val shield = equip.getOrNull(SLOT_SHIELD)
                if (shield == null) {
                    writeEmpty()
                } else {
                    writeItem(shield)
                }

                if (chest != null && hideArms(chest)) {
                    writeEmpty()
                } else {
                    writeClothes(look[3])//Arms
                }

                val legs = equip.getOrNull(SLOT_LEGS)
                if (legs == null) {
                    writeClothes(look[5])//Legs
                } else {
                    writeItem(legs)
                }

                val hat = equip.getOrNull(SLOT_HAT)
                val cape = equip.getOrNull(SLOT_CAPE)
                if (hat != null && hideHair(hat)) {
                    writeItem(hat)
                } else if(cape != null && equipment.getEquipType(cape) == HOODED_CAPE) {
                    writeEmpty()
                } else {
                    writeClothes(look[0])//Hair
                }

                val hands = equip.getOrNull(SLOT_HANDS)
                if (hands == null) {
                    writeClothes(look[4])//Bracelet
                } else {
                    writeItem(hands)
                }

                val shoes = equip.getOrNull(SLOT_FEET)
                if (shoes == null) {
                    writeClothes(look[6])//Feet
                } else {
                    writeItem(shoes)
                }

                if (hat != null && hideBeard(hat)) {
                    writeEmpty()
                } else {
                    writeClothes(look[1])//Beard
                }

                val aura = equip.getOrNull(SLOT_AURA)
                if (aura == null) {
                    writeEmpty()
                } else {
                    writeItem(aura)
                }

                //Inventory
                val stream = Unpooled.buffer()
                buffer.writeShort(0)
                buffer.writeBytes(stream)//TODO remove?
            }
            val colours = coloursMapper.get(entityId)?.colours ?: DEFAULT_COLOURS
            colours.forEach { writeByte(it) }
            writeShort(emoteMapper.get(entityId)?.id ?: 1426)//Render emote
            writeString(displayNameMapper.get(entityId)?.name ?: "")//Display name
            writeByte(combatLevelMapper.get(entityId)?.level ?: 3)//Combat level
            if (skillLevel) {
                writeShort(-1)//Skill level
            } else {
                writeByte(summoningCombatLevelMapper.get(entityId)?.level ?: 0)//Combat level + summoning
                writeByte(-1)//Skill level? or player height priority toggle
            }
            writeByte(transformMapper.has(entityId))//Npc morph
            //Morph details
            if (transformMapper.has(entityId)) {
                writeShort(-1)
                writeShort(-1)
                writeShort(-1)
                writeShort(0)
                writeByte(0)
            }
        }

        val data = ByteArray(packet.position())
        System.arraycopy(packet.buffer.array(), 0, data, 0, data.size)

        val appearance = appearanceDataMapper.get(entityId)
        appearance.hash = Encryption.encryptMD5(data)
        appearance.data = data
        //Flag for update
        appearanceMapper.create(entityId)
    }

    private fun hideHair(item: Item): Boolean {
        val def = definitions.get(item.type)
        val name = def.name
        val type = equipment.getEquipType(item)
        return type == HAIR || (type == MASK && !name.contains("beard"))
    }

    fun hideArms(item: Item): Boolean {
        return equipment.getEquipType(item) == FULL_BODY
    }

    fun hideBeard(item: Item): Boolean {
        val def = definitions.get(item.type)
        val name = def.name.toLowerCase()
        return hideHair(item)
                && !name.contains("horns")
                && !name.contains("hat")
                && !name.contains("afro")
                && name != "leather cowl"
                && !name.contains("headdress")
                && !name.contains("hood")
                && !isMask(name)
                && !isHelm(name)
    }

    private fun isHelm(name: String) = name.contains("helm") && !isHelmException(name)

    private fun isHelmException(name: String) = isFullHelm(name) || name.contains("decorative") || name.contains("verac") || name.contains("guthan") || name.contains("fishbowl") || name.contains("heraldic") || name.contains("lunar") || name.contains("tyras") || name.contains("slayer") || name.contains("cyclopean") || name.contains("wildstalker") || name.contains("trickster") || name.contains("vanguard")

    private fun isFullHelm(name: String) = name.contains("full") && !isFullHelmException(name)

    private fun isFullHelmException(name: String) = name.contains("third-age") || name.contains("statius")

    private fun isMask(name: String) = name.contains("mask") && !isMaskException(name)

    private fun isMaskException(name: String) = name.contains("h'ween") || name.contains("mime") || name.contains("frog") || name.contains("virtus") || name.contains("gorilla")

    companion object {
        private val DEFAULT_COLOURS = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        private val DEFAULT_LOOK = intArrayOf(0, 10, 18, 26, 33, 36, 42)
    }
}
