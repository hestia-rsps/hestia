package worlds.gregs.hestia.game.plugins.client.systems.update.bases.update

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.core.components.Created
import worlds.gregs.hestia.game.plugins.client.systems.update.update.PlayerUpdateSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.game.update.UpdateFlag

interface BaseUpdateSystem {
    fun flags(): List<UpdateFlag>

    fun isLocal(type: DisplayFlag?, update: Boolean): Boolean

    fun isGlobal(type: DisplayFlag?, update: Boolean): Boolean

    fun update(entityId: Int, local: Int, data: PacketBuilder, createdMapper: ComponentMapper<Created>, added: Boolean) {
        //Is it the entities initial update?
        val created = createdMapper.has(entityId) || added
        //Does the entity have any UpdateFlag's that need updating?
        val types = flags().filter { t -> (created && t.added) || t.subscription.entities.contains(local) }
        //Return if not
        if (types.isEmpty()) {
            return
        }

        var maskData = 0
        //Combine all the masks which need updating
        types.forEach {
            maskData = maskData or it.mask
        }

        if (maskData >= 256) {
            maskData = maskData or 0x80
        }
        if (maskData >= 65536) {
            maskData = maskData or if (this is PlayerUpdateSystem) 0x800 else 0x8000
        }

        data.writeByte(maskData)

        if (maskData >= 256) {//0x80
            data.writeByte(maskData shr 8)
        }
        if (maskData >= 65536) {//0x800
            data.writeByte(maskData shr 16)
        }

        //Write all of the update data
        types.forEach {
            it.unit.encode(data, entityId, local)
        }

        createdMapper.remove(entityId)
    }
}