package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.Marker
import world.gregs.hestia.game.component.update.Damage
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class HitsMask(private val damageMapper: ComponentMapper<Damage>, private val mob: Boolean) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { player, other ->
        val damage = damageMapper.get(other)
        if(mob) {
            writeByteC(damage.hits.size)
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
                writeByteC(percentage)
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