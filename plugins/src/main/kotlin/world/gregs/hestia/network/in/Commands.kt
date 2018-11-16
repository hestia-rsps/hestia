package world.gregs.hestia.network.`in`

import world.gregs.hestia.game.component.update.ForceMovement.Companion.SOUTH
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.update.direction.face
import world.gregs.hestia.game.component.update.direction.turn
import world.gregs.hestia.game.component.update.direction.watch
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
import world.gregs.hestia.game.component.entity.Type
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.update.*
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
            //Player updating flags
            "batch" -> {
                entity.batchAnim()
            }
            "colour" -> {
                //70 110 90 130 green used for kalphite king
                entity.colour(parts[1].toInt(), parts[2].toInt(), parts[3].toInt(), parts[4].toInt(), 1)
            }
            "bar" -> {
                entity.time()
            }
            "clan" -> {
                entity.world.players().forEach {
                    entity.world.getEntity(it).updateClanChat(true) }
            }
            "uk" -> {
                entity.edit().add(UpdateUnknown())
            }
            "p" -> {
                entity.world.players().forEach {
                    val other = entity.world.getEntity(it)
                    val dot = PlayerMiniMapDot()
                    dot.p = parts[1].toBoolean()
                    other.edit().add(dot)
                }
            }
            "model" -> {
                val mob = entity.world.getEntity(entity.world.mobs().first())
                mob.change()
//                mob.change(intArrayOf(390, 456, 332, 326, 151, 177, 12138, 181), intArrayOf(10508, -10342, 4550))
            }
            "party" -> {
                entity.schedule(0, 1) {
                    entity.colour(255, 255, tick * 25, 128, 1)
                    if(tick >= 10) {
                        stop()
                    }
                }
            }
            "anim" -> {
                entity.animate(parts[1].toInt())//863
            }
            "gfx" -> {
                entity.graphic(parts[1].toInt())//93
            }
            "transform" -> {
                val id = parts[1].toInt()
                if(id < 0) {
                    entity.updateAppearance()
                } else {
                    entity.transform(parts[1].toInt())
                }
            }
            "bot" -> {
//                for(i in 0 until 5)
                    es.dispatch(CreateBot("Bot"))
            }
            "b" -> {
                val bot = entity.world.getEntity(entity.world.players().last())
//                val position = bot.getComponent(Position::class)!!
//                bot.navigate(position.x + 1, position.y + 2)
                val position = bot.getComponent(Position::class)!!
                val target = Position.clone(position)
                target.y += 1
                bot.force(1, target, 2, ForceMovement.NORTH)
            }
            "mob" -> {
//                for(i in 0 until 5)
                    es.dispatch(CreateMob(1))
            }
            "m" -> {
                val mob = entity.world.getEntity(entity.world.mobs().first())
//                val position = mob.getComponent(Position::class)!!
//                mob.navigate(position.x + 1, position.y + 2)
//                mob.animate(2312)
//                mob.transform(mob.getComponent(Type::class)?.id ?: 0)
//                mob.colour(70, 110, 90, 130, duration = 60)
                entity.world.delete(entity.world.mobs().first())
            }
            "hit" -> {
                for(i in 0 until 5)
                entity.hit(10, Marker.MELEE)
            }
            "force" -> {
                val position = entity.getComponent(Position::class)!!
                val target = Position.clone(position)
                target.y += 1
                entity.force(1, target, 2, ForceMovement.SOUTH)
//                entity.force(p1, 1, SOUTH, target, 4)
            }
            "chat" -> {
                entity.force("Hello")
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
            //Mob updating flags
            "mobdemo" -> {
                val mob = entity.world.getEntity(entity.world.mobs().first())
                entity.schedule(0, 1) {
                    when(tick) {
                        0 -> {
                            mob.force("Graphic")
                            mob.graphic(40)
                            mob.graphic(60)
                            mob.graphic(80)
                            mob.graphic(110)
                        }
                        1 -> {
                            mob.force("Watch entity")
                            mob.watch(entity.id)
                        }
                        2 -> {
                            mob.force("Hits")
                            mob.watch(-1)
                            mob.hit(10)
                        }
                        3 -> {
                            mob.force("Time bar")
                            mob.time(increment = 1)
                        }
                        4 -> {
                            mob.force("Display name")
                            val displayName = DisplayName()
                            displayName.name = "Manly Man"
                            mob.edit().add(displayName).add(UpdateDisplayName())
                        }
                        5 -> {
                            mob.force("Transform")
                            mob.transform(0)
                        }
                        6 -> {
                            mob.transform(-1)
                            mob.force("Force chat")
                        }
                        7 -> {
                            mob.force("Face direction")
                            mob.face(0, -1)
                        }
                        8 -> {
                            mob.force("Combat level")
                            val update = CombatLevel()
                            update.level = 1000
                            mob.edit().add(update).add(UpdateCombatLevel())
                        }
                        9 -> {
                            mob.force("Force movement")
                            val position = mob.getComponent(Position::class)!!
                            val target = Position.clone(position)
                            target.y -= 1
                            mob.force(1, target, 2, ForceMovement.SOUTH)
                        }
                        11 -> {
                            mob.force("Animations")
                            mob.animate(855)
                            mob.animate(856)
                            mob.animate(857)
                            mob.animate(858)
                        }
                        12 -> {
                            mob.force("Change models & colours")
                            mob.change(intArrayOf(390, 456, 332, 326, 151, 177, 12138, 181), intArrayOf(10508, -10342, 4550))
                        }
                        13 -> {
                            mob.force("Colour overlay")
                            mob.colour(70, 110, 90, 130, duration = 60)
                        }
                        14 -> {
                            mob.force("Finish")
                            mob.change()
                        }
                        15 -> {
                            stop()
                        }
                    }
                }
            }
            "botdemo" -> {
                val bot = entity.world.getEntity(entity.world.players()[1])
                entity.schedule(0, 1) {
                    when(tick) {
                        0 -> {
                            bot.force("Animation")
                            bot.animate(855)
                            bot.animate(856)
                            bot.animate(857)
                            bot.animate(858)
                        }
                        1 -> {
                            bot.force("Graphic")
                            bot.graphic(40)
                            bot.graphic(60)
                            bot.graphic(80)
                            bot.graphic(110)
                        }
                        2 -> {
                            bot.force("Colour overlay")
                            bot.colour(70, 110, 90, 130, duration = 60)
                        }
                        3 -> {
                            bot.force("Time bar")
                            bot.time(increment = 1)
                        }
                        4 -> {
                            bot.force("Clan chat member")
                            entity.updateClanChat(true)
                        }
                        5 -> {
                            bot.force("Hits")
                            bot.hit(10)
                        }
                        6 -> {
                            bot.force("Appearance")
                            bot.updateAppearance()
                        }
                        7 -> {
                            bot.force("Force chat")
                        }
                        8 -> {
                            bot.force("Watch entity")
                            bot.watch(entity.id)
                        }
                        9 -> {
                            bot.force("Force movement")
                            bot.watch(-1)
                            val position = bot.getComponent(Position::class)!!
                            val target = Position.clone(position)
                            target.y -= 1
                            bot.force(1, target, 2, ForceMovement.SOUTH)
                        }
                        10 -> {
                            bot.force("Face")
                            bot.turn(-1, 0)
                        }
                    }
                }
            }
            else -> return false
        }
        return true
    }

}