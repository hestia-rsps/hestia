package worlds.gregs.hestia.content.command

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.components.types.MoveStep

lateinit var followingMapper: ComponentMapper<Following>
lateinit var shiftMapper: ComponentMapper<Following>
lateinit var stepsMapper: ComponentMapper<Steps>

on<Command> {
    where { prefix == "tele" || prefix == "tp" }
    then {

        fun move(x: Int, y: Int, plane: Int) {
            //TODO temp clearing, needs a proper system
            followingMapper.remove(entity)
            shiftMapper.remove(entity)
            stepsMapper.get(entity)?.clear()
//            entity.send()//TODO send minimap flag removal
            entity.create(MoveStep::class).set(x, y, plane)
        }

        if (content.contains(",")) {
            val params = content.split(",")
            val plane = params[0].toInt()
            val x = params[1].toInt() shl 6 or params[3].toInt()
            val y = params[2].toInt() shl 6 or params[4].toInt()
            move(x, y, plane)
        } else {
            val parts = content.split(" ")
            move(parts[0].toInt(), parts[1].toInt(), if (parts.size > 2) parts[2].toInt() else 0)
        }
        isCancelled = true
    }
}