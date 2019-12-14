package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.send
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.artemis.events.Disconnect
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueSystem
import worlds.gregs.hestia.core.display.update.model.components.*
import worlds.gregs.hestia.core.display.widget.model.components.screen.CustomScreenWidget
import worlds.gregs.hestia.core.display.widget.logic.systems.screen.CustomScreenWidgetSystem
import worlds.gregs.hestia.core.entity.bot.model.events.CreateBot
import worlds.gregs.hestia.core.entity.entity.logic.*
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.mob.logic.systems.change
import worlds.gregs.hestia.core.entity.mob.model.events.CreateMob
import worlds.gregs.hestia.core.entity.player.logic.systems.updateClanChat
import worlds.gregs.hestia.core.entity.player.model.components.update.Hidden
import worlds.gregs.hestia.core.getComponent
import worlds.gregs.hestia.core.task.tick.model.schedule
import worlds.gregs.hestia.core.mobs
import worlds.gregs.hestia.core.remove
import worlds.gregs.hestia.core.toggle
import worlds.gregs.hestia.core.world.movement.model.components.RandomWalk
import worlds.gregs.hestia.core.world.movement.model.components.RunToggled
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Follow
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step
import worlds.gregs.hestia.core.world.region.logic.systems.RegionBuilderSystem
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.game.update.blocks.Marker
import worlds.gregs.hestia.network.client.decoders.messages.ConsoleCommand
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.service.*

class ConsoleCommandHandler : MessageHandlerSystem<ConsoleCommand>() {

    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: ConsoleCommand) {
        val (command) = message
        handle(entityId, command)
    }

    private fun handle(entityId: Int, command: String, parts: List<String> = command.split(" ")) {

        if(command.contains(" ")) {
            es.dispatch(Command(entityId, command.split(" ")[0], command))
        } else {
            es.dispatch(Command(entityId, command))
        }

        val entity = world.getEntity(entityId)

        if (command.contains(",")) {
            val params = parts[1].split(",")
            val plane = params[0].toInt()
            val x = params[1].toInt() shl 6 or params[3].toInt()
            val y = params[2].toInt() shl 6 or params[4].toInt()

            //TODO temp clearing, needs a proper system
            entity.edit().remove(Follow::class).remove(Shift::class)
            entity.getComponent(Steps::class)?.clear()
//            entity.send()//TODO send minimap flag removal
            entity.move(x, y, plane)
            return
        }

        println("Command ${parts[0]}")
        when (parts[0]) {
            "text" -> {
                entity.send(WidgetComponentText(parts[1].toInt(), parts[2].toInt(), parts[3]))
            }
            "di" -> {
//                val queue = SuspendingQueue()
//                entity.edit().add(queue)
//                queue.queue.add(SuspendingCoroutine(EmptyCoroutineContext) {
//                    println("Do a thing")
//                    wait(4)
//                    println("Do something else")
//                    wait(2)
//                    println("Done!")
//                })
                world.getSystem(DialogueSystem::class).startDialogue(entityId, "Man")
            }
            "inter" -> {
                val id = parts[1].toInt()
                if(entity.getComponent(CustomScreenWidget::class) == null) {
                    entity.edit().add(CustomScreenWidget(id))
                } else {
                    if(id == -1) {
                        entity.edit().remove(CustomScreenWidget::class)
                    } else {
                        entity.getComponent(CustomScreenWidget::class)!!.id = id
                        world.getSystem(CustomScreenWidgetSystem::class).open(entityId)
                    }
                }
            }
            "config" -> {
                es.send(entityId, Config(parts[1].toInt(), parts[2].toInt()))
            }
            "configf" -> {
                es.send(entityId, ConfigFile(parts[1].toInt(), parts[2].toInt()))
            }
            "tele", "tp" -> {
                entity.move(parts[1].toInt(), parts[2].toInt(), if(parts.size > 3) parts[3].toInt() else 0)
            }
            "follow" -> {
                if(entity.getComponent(Follow::class) == null) {
                    val position = entity.getComponent(Position::class)!!
                    entity.step(position.x + 1, position.y)
                    entity.edit().add(Follow(world.players().get(world.players().size() - 1)))
                } else {
                    entity.edit().remove(Follow::class.java)
                }
            }
            "dy" -> {
                val position = entity.getComponent(Position::class)!!
                val builder = world.getSystem(RegionBuilderSystem::class)
//                dynamicRegionSystem.create(position.regionId)
//                if(regionSystem.toggle(position.regionId)) {
//                builder.reset(position.regionId)
                builder.clear(position.regionId)
//                builder.set(position.chunkX, position.chunkY, 0, position.chunkX, position.chunkY, 0, parts[1].toInt())
//                builder.set(position.chunkX, position.chunkY, 0, position.chunkX, position.chunkY, 0, 8, 8, parts[1].toInt())
                builder.set(12342, 12342)
//                builder.set(0, 0, 0, 383, 439, 0)
//                builder.set(0, 0, 0, 386, 435, 0, 2, 3, 0)
//                regionSystem.changeArea(position.chunkX + 1, position.chunkY - 1, 0, position.chunkX - 1, position.chunkY - 1, 0, 3, 2, 3)
//                    regionSystem.changeRegion(position.regionId, position.regionId, 3)
//                }
                if(builder.build(position.regionId)) {
                    entity.updateMapRegion(local = false, forceRefresh = true)
                } else {
                    println("Failed to load region ${position.regionId}")
                }

            }
            //Player updating flags
            "batch" -> {
                entity.batchAnim()
            }
            "hide" -> {
                entity.edit().toggle(Hidden())
                entity.updateAppearance()
            }
            "colour" -> {
                //70 110 90 130 green used for kalphite king
                entity.colour(parts[1].toInt(), parts[2].toInt(), parts[3].toInt(), parts[4].toInt(), 10)
            }
            "bar" -> {
                entity.time()
            }
            "clan" -> {
                world.players().forEach {
                    world.getEntity(it).updateClanChat(true)
                }
            }
            "uk" -> {
                entity.edit().add(UpdateUnknown())
            }
            "p" -> {
                world.players().forEach {
                    val other = world.getEntity(it)
                    other.edit().add(PlayerMiniMapDot(parts[1].toBoolean()))
                }
            }
            "model" -> {
                val mob = world.getEntity(world.mobs().get(0))
                mob.change()
//                mob.change(intArrayOf(390, 456, 332, 326, 151, 177, 12138, 181), intArrayOf(10508, -10342, 4550))
            }
            "players" -> {
                println(world.players().size())
            }
            "mobs" -> {
                println(world.mobs().size())
            }
            "party" -> {
                val position = entity.getComponent(Position::class)!!
                var count = 0
//                es.dispatch(CreateBot("Bot ${count++}", position.x, position.y))
                val size = 22
                for (y in position.y.nearby(size)) {
                    for (x in position.x.nearby(size)) {
                        es.dispatch(CreateBot("Bot ${count++}", x, y))
                    }
                }
                println("Created $count")
                world.schedule(1, 1) {
                    when(tick) {
                        2 -> {
                            world.players().toArray().filterNot { it == entityId }.forEach {
                                val bot = world.getEntity(it)
                                bot.edit().add(RandomWalk())
                            }
                        }
                        3 -> stop()
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
                if (id < 0) {
                    entity.updateAppearance()
                } else {
                    entity.transform(parts[1].toInt())
                }
            }
            "bot" -> {
                val position = entity.getComponent(Position::class)!!
                var count = 0
                    es.dispatch(CreateBot("Bot ${count++}", position.x, position.y + 200))
                println("Created ${world.players().get(world.players().size() - 1)}")//16762
//                for (y in position.y.nearby(11)) {
//                    for (x in position.x.nearby(11)) {
//                        es.dispatch(CreateBot("Bot ${count++}", x, y))
//                    }
//                }
                /*es.dispatch(CreateBot("Bot ${count++}", position.x, position.y + 1))
                es.dispatch(CreateBot("Bot ${count++}", position.x, position.y + 2))
                es.dispatch(CreateBot("Bot ${count++}", position.x, position.y + 3))
                world.schedule(1, 1) {
                    when(tick) {
                        0 -> es.dispatch(Disconnect(world.players()[1]))
                        3 -> es.dispatch(CreateBot("Bot 0", position.x, position.y + 4))
                        4 -> stop()
                    }
                    val first = world.getEntity(world.players().get(1))
                    val second = world.getEntity(world.players().get(2))
                    first.step(position.x, position.y + 4)
                    second.step(position.x, position.y + 1)
                }*/
//                    es.dispatch(CreateBot("Bot ${count++}", position.x, position.y + 200))
            }
            "b" -> {
                val bot = world.getEntity(world.players().get(world.players().size() - 1))
                println("Remove bot ${bot.id}")
                es.dispatch(Disconnect(bot))
//                bot.edit().add(Follow(entityId)).add(RunToggled())
                /*bot.edit().add(Hidden())
                bot.updateAppearance()*/
//                world.deleteEntity(bot)
            }
            "mob" -> {
                val position = entity.getComponent(Position::class)!!
//                es.dispatch(CreateMob(1, position.x, position.y + 1))

                for (y in position.y.nearby(11)) {
                    for (x in position.x.nearby(11)) {
                        es.dispatch(CreateMob(1, x, y))
                    }
                }
                /*entity.schedule(4, 0) {
                    world.mobs().forEachIndexed { index, it ->
                        val displayName = DisplayName()
                        displayName.name = "Mob ${index + 1} ${3482 + index}"
                        world.getEntity(it).edit().add(displayName).add(UpdateDisplayName())
                    }
                    stop()
                }*/
            }
            "m" -> {
                world.mobs().forEach {
                    world.delete(it)
//                    val mob = world.getEntity(it)
//                    val position = mob.getComponent(Position::class)!!
//                    println("$position")
//                    mob.step(position.x, position.y - 2)
                }
                /*world.mobs().forEach {
                    val mob = world.getEntity(it)
                    mob.force("Animations")
                    mob.animate(855)
                    mob.animate(856)
                    mob.animate(857)
                    mob.animate(858)
//                    world.getEntity(it).edit().add(Stalk(entityId))
                }*/
//                mob.animate(2312)
//                mob.transform(mob.getComponent(Type::class)?.id ?: 0)
//                mob.colour(70, 110, 90, 130, duration = 60)
//                world.delete(world.mobs().first())
            }
            "hit" -> {
                for (i in 0 until 5)
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
                val move = Step()
                move.x = position.x + 1
                move.y = position.y - 2
                entity.edit()?.add(move)
            }
            "run" -> {
                entity.edit().toggle(RunToggled()).add(UpdateMovement())
            }
            "turn" -> {
                entity.turn(-1, 0)
            }
            "face" -> {
                val position = entity.getComponent(Position::class)!!
                entity.face(position.x, position.y - 1)
            }
            "watch" -> {
                entity.watch(world.players().get(world.players().size() - 1))
            }
            //Mob updating flags
            "mobdemo" -> {
                val mob = world.getEntity(world.mobs().get(0))
                world.schedule(0, 1) {
                    when (tick) {
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
                            mob.edit().add(DisplayName("Manly Man")).add(UpdateDisplayName())
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
                            mob.change(intArrayOf(390, 456, 332, 326, 151, 177, 12138, 181), intArrayOf(10508, -10342, 4550, 8))
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
                val bot = world.getEntity(world.players()[1])
                world.schedule(0, 1) {
                    when (tick) {
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
        }
    }
}