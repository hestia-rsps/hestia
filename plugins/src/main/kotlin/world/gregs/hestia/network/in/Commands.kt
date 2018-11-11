package world.gregs.hestia.network.`in`

import world.gregs.hestia.game.component.update.ForceMovement.Companion.SOUTH
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.update.direction.face
import world.gregs.hestia.game.component.update.direction.turn
import world.gregs.hestia.game.component.update.direction.watch
import world.gregs.hestia.game.component.update.force
import world.gregs.hestia.game.component.movement.*
import world.gregs.hestia.game.events.*
import world.gregs.hestia.network.login.Packets
import world.gregs.hestia.services.getComponent
import world.gregs.hestia.services.mobs
import world.gregs.hestia.services.players
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.game.update.Marker
import world.gregs.hestia.network.game.GamePacket

@PacketSize(-1)
@PacketOpcode(Packets.COMMAND)
class Commands : GamePacket() {
    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        packet.readUnsignedByte() // client command
        packet.readUnsignedByte()
        val command = packet.readString()
        val parts = command.split(" ")

        val entity = entity ?: return false

        if(command.contains(",")) {
            val params = parts[1].split(",")
            val plane = params[0].toInt()
            val x = params[1].toInt() shl 6 or params[3].toInt()
            val y = params[2].toInt() shl 6 or params[4].toInt()

            entity.move(x, y, plane)
            return true
        }
        println("Command ${parts[0]}")
        when(parts[0]) {
            "bot" -> {
                es.dispatch(CreateBot("Bot"))
            }
            "b" -> {
//                val bot = entity.world.getEntity(entity.world.players().last())
//                val position = bot.getComponent(Position::class)!!
//                bot.navigate(position.x + 1, position.y + 2)
                entity.world.delete(entity.world.players().last())
            }
            "mob" -> {
                es.dispatch(CreateMob(1))
            }
            "m" -> {
                val mob = entity.world.getEntity(entity.world.mobs().first())
                val position = mob.getComponent(Position::class)!!
                mob.navigate(position.x + 1, position.y + 2)
            }
            "hit" -> {
                for(i in 0 until 5)
                entity.hit(10, Marker.MELEE)
            }
            "force" -> {
                val position = entity.getComponent(Position::class)!!
                val p1 = Position.clone(position)
                p1.x += 1
                val target = Position.clone(position)
                target.y -= 4
                entity.force(p1, 1, SOUTH, target, 4)
            }
            "chat" -> {
                entity.force("Hello")
            }
            "anim" -> {
                entity.animate(parts[1].toInt())//863
            }
            "gfx" -> {
                entity.graphic(parts[1].toInt())//93
            }
            "pos" -> {
                val position = entity.getComponent(Position::class)
                println("${position?.x ?: -1}, ${position?.y ?: -1}, ${position?.plane ?: -1}")
            }
            "move" -> {
                val position = entity.getComponent(Position::class)!!
                val move = Navigate()
                move.x = position.x + 1
                move.y = position.y - 2
                entity.edit()?.add(move)
            }
            "run" -> {
                entity.edit().add(Running()).add(UpdateMovement())
            }
            "turn" -> {
                entity.turn(-1, 0)
            }
            "face" -> {
                val position = entity.getComponent(Position::class)!!
                entity.face(position.x, position.y - 1)
            }
            "watch" -> {
                entity.watch(1)
            }
            else -> return false
        }
        return true
    }

}