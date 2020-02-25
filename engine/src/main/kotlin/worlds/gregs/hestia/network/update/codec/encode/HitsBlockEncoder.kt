package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.HitsBlock
import worlds.gregs.hestia.game.update.blocks.Marker
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class HitsBlockEncoder(private val npc: Boolean) : UpdateBlockEncoder<HitsBlock> {

    override fun encode(builder: PacketBuilder, block: HitsBlock) {
        val (_, damage, player, other) = block
        builder.apply {
            if (npc) {
                writeByte(damage.size, Modifier.INVERSE)
            } else {
                writeByte(damage.size)
            }
            damage.forEach { hit ->
                if (hit.damage == 0 && !interactingWith(player, other, hit.source)) {
                    writeSmart(32766)
                    return@forEach
                }

                val mark = calcMark(player, other, hit)
                val percentage = 255//TODO when added skills

                if (hit.soak != -1) {
                    writeSmart(32767)
                }

                writeSmart(mark)
                writeSmart(hit.damage)

                if (hit.soak != -1) {
                    writeSmart(Marker.ABSORB)
                    writeSmart(hit.soak)
                }

                writeSmart(hit.delay)
                if(npc) {
                    writeByte(percentage)
                } else {
                    writeByte(percentage, Modifier.INVERSE)
                }
            }
        }
    }

    private fun calcMark(player: Int, other: Int, marker: Marker): Int {
        if (marker.mark == Marker.HEALED) {
            return marker.mark
        }

        if (marker.damage == 0) {
            return Marker.MISSED
        }

        var mark = marker.mark

        if (marker.critical) {
            mark += 10
        }

        if (!interactingWith(player, other, marker.source)) {
            mark += 14
        }

        return mark
    }

    private fun interactingWith(player: Int, victim: Int, source: Int): Boolean {
        return player == victim || player == source
    }

}