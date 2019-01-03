package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.game.PacketHandler
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.calc.Follow
import worlds.gregs.hestia.game.plugins.movement.components.calc.Path
import worlds.gregs.hestia.game.plugins.movement.strategies.FixedTileStrategy
import worlds.gregs.hestia.network.login.Packets

@PacketSize(-1)
@PacketOpcode(Packets.WALKING, Packets.MINI_MAP_WALKING)
class WalkingHandler : PacketHandler() {
    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var followMapper: ComponentMapper<Follow>
    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun handle(entityId: Int, packet: Packet, length: Int) {
        val size = if (packet.opcode == Packets.MINI_MAP_WALKING) length - 13 else length
        val baseX = packet.readLEShortA()
        val baseY = packet.readLEShortA()
        val running = packet.readByte() == 1
        val steps = Math.min((size - 5) / 2, 25)

        if (steps <= 0) {
            return
        }

        var x = 0
        var y = 0

        for (step in 0 until steps) {
            x = baseX + packet.readUnsignedByte()
            y = baseY + packet.readUnsignedByte()
        }

        //TODO temp clearing, needs a proper system
        //Clear current steps
        stepsMapper.get(entityId)?.clear()

        //Clear follow
        followMapper.remove(entityId)

        //Clear any movement
        shiftMapper.remove(entityId)

        //Calculate path
        pathMapper.create(entityId).apply {
            this.strategy = FixedTileStrategy(x, y)
        }
    }

}