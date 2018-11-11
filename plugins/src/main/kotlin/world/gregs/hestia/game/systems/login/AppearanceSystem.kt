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

class AppearanceSystem : SubscriptionSystem(Aspect.all(Player::class)) {

    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var appearanceMapper: ComponentMapper<Appearance>

    override fun inserted(entityId: Int) {
        update(UpdateAppearance(entityId))
    }

    @Subscribe
    private fun update(event: UpdateAppearance) {
        val packet = Packet.Builder()
        val displayName = displayNameMapper.get(event.entityId).name

        packet.apply {

            writeByte(0)//Flag
            writeByte(-1)//Title
            writeString("<img=1>")//Chat prefix
            writeByte(-1)//Skull
            writeByte(-1)//Head icon
            writeByte(0)//Hidden

            for (index in 0..3) {
                writeByte(0)//Hidden appearance
            }

            val look = intArrayOf(0, 10, 18, 26, 33, 36, 42)
            val colour = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

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

            colour.forEach { writeByte(it) }
            writeShort(1426)//Render emote
            writeString(displayName ?: "")//Display name
            writeByte(3)//Combat level
            writeByte(0)//Combat level + summoning
            writeByte(-1)//Player height priority toggle?
            writeByte(0)//Mob morph
        }

        val data = ByteArray(packet.position())
        System.arraycopy(packet.buffer.array(), 0, data, 0, data.size)

        val appearance = appearanceDataMapper.get(event.entityId)
        appearance.hash = Encryption.encryptMD5(data)
        appearance.data = data
        //Flag for update
        appearanceMapper.create(event.entityId)
    }
}
