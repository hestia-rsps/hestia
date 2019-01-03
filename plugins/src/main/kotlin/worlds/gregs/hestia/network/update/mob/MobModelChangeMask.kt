package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.mob.component.update.ModelChange

class MobModelChangeMask(private val modelChangeMapper: ComponentMapper<ModelChange>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
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

        writeByteS(hash)
        models?.forEach {
            writeShort(it)
        }
        colours?.forEach {
            writeShort(it)
        }
        textures?.forEach {
            writeLEShortA(it)
        }
    }

}