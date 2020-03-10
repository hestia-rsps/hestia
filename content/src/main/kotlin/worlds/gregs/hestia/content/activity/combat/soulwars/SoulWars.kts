import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.NpcOption
import worlds.gregs.hestia.core.action.model.ObjectOption
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue.Companion.FIRST
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue.Companion.SECOND
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Disregard
import worlds.gregs.hestia.core.display.dialogue.model.Expression.EvilLaugh
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Shock
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.npc.logic.systems.npcSpawn
import worlds.gregs.hestia.core.task.model.await.Route
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem.Companion.isNear
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.components.types.MoveStep
import worlds.gregs.hestia.core.world.movement.model.events.Follow
import worlds.gregs.hestia.core.world.movement.model.events.Interact
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem
import kotlin.random.Random

npcSpawn(8591, 1893, 3177, direction = Direction.SOUTH)//NOMAD
npcSpawn(8526, 1888, 3176, direction = Direction.NORTH)//ZIMBERFIZZ

on(ObjectOption, "Enter", "Soul Wars portal") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity perform Interact(target, true)

        val route = await(Route())
        val definition = system(ObjectDefinitionSystem::class).get(target.get(GameObject::class).id)
        val canInteract = route.steps >= 0 && !route.alternative || isNear(entity get Position::class, target get Position::class, definition.sizeX, definition.sizeY, true)
        await(Ticks(1))
        if (!canInteract) {
            entity perform Face(target get Position::class)//TODO object def size
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }

        entity remove Following::class
        entity remove Shift::class
        entity.get(Steps::class).clear()
        entity.create(MoveStep::class).set(1886, 3178)
    }
    then(EntityActions::task)
}

on(ObjectOption, "Leave-area", "Edgeville portal") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity perform Interact(target, true)

        val route = await(Route())
        val definition = system(ObjectDefinitionSystem::class).get(target.get(GameObject::class).id)
        val canInteract = route.steps >= 0 && !route.alternative || isNear(entity get Position::class, target get Position::class, definition.sizeX, definition.sizeY, true)
        await(Ticks(1))
        if (!canInteract) {
            entity perform Face(target get Position::class)//TODO object def size
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }

        entity remove Following::class
        entity remove Shift::class
        entity.get(Steps::class).clear()
        entity.create(MoveStep::class).set(3082, 3474)
    }
    then(EntityActions::task)
}

on(ObjectOption, "Join-team", "Balance portal") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity perform Interact(target, true)

        val route = await(Route())
        val definition = system(ObjectDefinitionSystem::class).get(target.get(GameObject::class).id)
        val canInteract = route.steps >= 0 && !route.alternative || isNear(entity get Position::class, target get Position::class, definition.sizeX, definition.sizeY, true)
        await(Ticks(1))
        if (!canInteract) {
            entity perform Face(target get Position::class)//TODO object def size
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }

        entity remove Following::class
        entity remove Shift::class
        entity.get(Steps::class).clear()

        dialogue {
            entity statement """
                <col=ff0000>Warning:<col=000000> a game is currently in progress. If you enter the portal,
                you will be added to the game as soon as possible.
            """
            val choice = entity options """
                Enter portal.
                Stay outside.
            """
            if(choice == FIRST) {
                //TODO random walkable position in area
                if(Random.nextBoolean()) {// TODO weighted team calculation
                    entity.create(MoveStep::class).set(1874, 3162)// Blue
                } else {
                    entity.create(MoveStep::class).set(1904, 3162)// Red
                }
//                Blue - 1870, 3158 - 1879, 3166
//                Red - 1900, 3157 - 1909, 3166
            }
        }
    }
    then(EntityActions::task)
}

on(NpcOption, "Talk-to", "Nomad") { ->
    fun EntityActions.task(npc: Int) = strongQueue {
        entity perform Follow(npc)
        val within = await(WithinRange(npc, 1))
        entity perform Follow(-1)

        if (!within) {
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }
        val firstTime = true

        dialogue {
            if (firstTime) {
                entity animation Disregard dialogue "Excuse me, what is this place?"
                npc perform Watch(entity)
                npc animation Shock dialogue """
                    What is this place? This is Soul Wars, an ancient
                    battleground where you must fight to crush the souls of
                    your enemies!
                """
                entity animation Disregard dialogue "Really? Are souls easy to crush?"
                npc animation EvilLaugh dialogue "Easier than you'd imagine."
            } else {
                npc perform Watch(entity)
                npc dialogue "Aha, you again. How can I help?"
            }

            var choice = entity options """
                    Can you tell me more about Soul Wars?
                    I have to go; I think I left my house on fire.
                """
            when(choice) {
                FIRST -> {
                    npc dialogue """
                        Of course! I can give you a brief demonstration, or a book
                        to read in your own time.
                    """
                    choice = entity options """
                        Could you give me the demonstration?
                        The book sounds good; could I have a copy?
                        Can't I just get started?
                    """

                    when(choice) {
                        FIRST -> {
                            npc dialogue "Very well, I'll take us there now."
                        }
                    }
                }
                SECOND -> {
                }
            }
        }
    }
    then(EntityActions::task)
}