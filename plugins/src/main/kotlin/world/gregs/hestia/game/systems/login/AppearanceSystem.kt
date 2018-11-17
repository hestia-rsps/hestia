package world.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.Appearance
import world.gregs.hestia.game.component.update.AppearanceData
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.events.UpdateAppearance
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import io.netty.buffer.Unpooled
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.Encryption
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.component.update.Transform

class AppearanceSystem : SubscriptionSystem(Aspect.all(Player::class)) {

    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var appearanceMapper: ComponentMapper<Appearance>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var playerMapper: ComponentMapper<Player>

    override fun inserted(entityId: Int) {
        update(entityId)
    }

    @Subscribe
    fun update(event: UpdateAppearance) {
        update(event.entityId)
    }

    private fun update(entityId: Int) {
        if(!playerMapper.has(entityId)) {
            return
        }

        val packet = Packet.Builder()
        val displayName = displayNameMapper.get(entityId).name

        val look = intArrayOf(0, 10, 18, 26, 33, 36, 42)
        val colour = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        packet.apply {

            writeByte(0)//Flag
            writeByte(-1)//Title
            writeString("<img=1>")//Chat prefix
            writeByte(-1)//Skull
            writeByte(-1)//Head icon
            writeByte(0)//Hidden

            if(transformMapper.has(entityId)) {
                val transform = transformMapper.get(entityId)
                writeShort(-1)
                writeShort(transform.mobId)
                writeByte(0)
            } else {
                for (index in 0..3) {
                    writeByte(0)//Hidden appearance
                }

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
            colour.forEach { writeByte(it) }
            writeShort(1426)//Render emote
            writeString(displayName ?: "")//Display name
            writeByte(3)//Combat level
            writeByte(0)//Combat level + summoning
            writeByte(-1)//Player height priority toggle?
            writeByte(transformMapper.has(entityId).int)//Mob morph
            //Morph details
            if(transformMapper.has(entityId)) {
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
}
