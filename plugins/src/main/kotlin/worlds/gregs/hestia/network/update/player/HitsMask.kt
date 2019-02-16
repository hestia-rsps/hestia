package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.Damage
import worlds.gregs.hestia.game.update.Marker
import worlds.gregs.hestia.game.update.UpdateEncoder

class HitsMask(private val damageMapper: ComponentMapper<Damage>, private val mob: Boolean) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { player, other ->
        val damage = damageMapper.get(other)
        if(mob) {
            writeByte(damage.hits.size, Modifier.INVERSE)
        } else {
            writeByte(damage.hits.size)
        }
        damage.hits.forEach { hit ->
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
            if(mob) {
                writeByte(percentage)
            } else {
                writeByte(percentage, Modifier.INVERSE)
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