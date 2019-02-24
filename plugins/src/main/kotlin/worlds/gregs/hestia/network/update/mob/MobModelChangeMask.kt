package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.mob.component.update.ModelChange
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobModelChangeMask(private val modelChangeMapper: ComponentMapper<ModelChange>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val modelChange = modelChangeMapper.get(other)
        val models = modelChange.models
        val colours = modelChange.colours
        val textures = modelChange.textures

        var hash = 0
        //Reset
        if(models == null && colours == null && textures == null) {
            hash = 1
        }

        if(models != null) {
            hash = hash or 0x2
        }
        if(colours != null) {
            hash = hash or 0x4
        }
        if(textures != null) {
            hash = hash or 0x8
        }

        writeByte(hash, Modifier.SUBTRACT)
        models?.forEach {
            writeShort(it)
        }
        colours?.forEach {
            writeShort(it)
        }
        textures?.forEach {
            writeShort(it, Modifier.ADD, Endian.LITTLE)
        }
    }

}