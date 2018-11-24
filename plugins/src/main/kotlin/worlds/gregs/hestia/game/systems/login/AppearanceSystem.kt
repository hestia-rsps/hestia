package worlds.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.events.UpdateAppearance
import worlds.gregs.hestia.game.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import io.netty.buffer.Unpooled
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.Encryption
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.component.update.Transform
import worlds.gregs.hestia.game.component.update.appearance.*

class AppearanceSystem : SubscriptionSystem(Aspect.all(Player::class)) {

    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var appearanceMapper: ComponentMapper<Appearance>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var playerMapper: ComponentMapper<Player>
    private lateinit var coloursMapper: ComponentMapper<Colours>
    private lateinit var bodyMapper: ComponentMapper<Body>
    private lateinit var namePrefixMapper: ComponentMapper<NamePrefix>
    private lateinit var titleMapper: ComponentMapper<Title>
    private lateinit var skullMapper: ComponentMapper<Skull>
    private lateinit var headIconMapper: ComponentMapper<HeadIcon>
    private lateinit var hiddenMapper: ComponentMapper<Hidden>
    private lateinit var emoteMapper: ComponentMapper<Emote>
    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>
    private lateinit var summoningCombatLevelMapper: ComponentMapper<SummoningCombatLevel>

    override fun inserted(entityId: Int) {
        update(entityId)
    }

    @Subscribe
    fun update(event: UpdateAppearance) {
        update(event.entityId)
    }

    private fun update(entityId: Int) {
        if (!playerMapper.has(entityId)) {
            return
        }

        val packet = Packet.Builder()

        packet.apply {
            val skillLevel = false
            var flag = 0
//            flag = flag or 0x1//gender
//            flag = flag or 0x2//Has display name
            if(skillLevel) {
                flag = flag or 0x4//Skill level displayed rather than combat
            }
//            flag = flag or (size shl 3 and 0x7)//Size
//            flag = flag and ((1 and 0xf2) shr 6)//Something about trimming title
            writeByte(flag)//Flag
            writeByte(titleMapper.get(entityId)?.title ?: 1)//Title
            writeString(namePrefixMapper.get(entityId)?.prefix ?: "")//Chat prefix
            writeByte(skullMapper.get(entityId)?.skull ?: -1)//Skull
            writeByte(headIconMapper.get(entityId)?.headIcon ?: -1)//Head icon
            writeByte(hiddenMapper.has(entityId).int)//Hidden (displays visible to admins)

            if (transformMapper.has(entityId)) {
                val transform = transformMapper.get(entityId)
                writeShort(-1)
                writeShort(transform.mobId)
                writeByte(0)
            } else {
                for (index in 0 until 4) {
                    writeByte(0)//Hidden appearance
                }

                val look = bodyMapper.get(entityId)?.look ?: DEFAULT_LOOK

                writeShort(0x100 + look[2])//Torso
                writeByte(0)
                writeShort(0x100 + look[3])//Arms

                writeShort(0x100 + look[5])//Legs
                writeShort(0x100 + look[0])//Hair
                writeShort(0x100 + look[4])//Bracelet
                writeShort(0x100 + look[6])//Feet
                writeShort(0x100 + look[1])//Beard
                writeByte(0)

                //Inventory
                val stream = Unpooled.buffer()
                for (slotId in 0..14) {
                }
                buffer.writeShort(0)
                buffer.writeBytes(stream)//TODO remove?
            }
            val colours = coloursMapper.get(entityId)?.colours ?: DEFAULT_COLOURS
            colours.forEach { writeByte(it) }
            writeShort(emoteMapper.get(entityId)?.id ?: 1426)//Render emote
            writeString(displayNameMapper.get(entityId)?.name ?: "")//Display name
            writeByte(combatLevelMapper.get(entityId)?.level ?: 3)//Combat level
            if(skillLevel) {
                writeShort(-1)//Skill level
            } else {
                writeByte(summoningCombatLevelMapper.get(entityId)?.level ?: 0)//Combat level + summoning
                writeByte(-1)//Skill level? or player height priority toggle
            }
            writeByte(transformMapper.has(entityId).int)//Mob morph
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

    companion object {
        private val DEFAULT_COLOURS = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        private val DEFAULT_LOOK = intArrayOf(0, 10, 18, 26, 33, 36, 42)
    }
}
