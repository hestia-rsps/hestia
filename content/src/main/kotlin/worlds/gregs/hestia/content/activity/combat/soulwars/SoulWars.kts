import arrow.core.andThen
import com.artemis.ComponentMapper
import worlds.gregs.hestia.content.activity.combat.equipment.EquippedItem
import worlds.gregs.hestia.content.activity.combat.equipment.UnequippedItem
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
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SoulWarsRewards
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.components.ForceMovement
import worlds.gregs.hestia.core.display.update.model.components.ForceMovement.Companion.EAST
import worlds.gregs.hestia.core.display.update.model.components.ForceMovement.Companion.WEST
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Blackboard
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.events.Animation
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_CAPE
import worlds.gregs.hestia.core.entity.item.container.logic.equip
import worlds.gregs.hestia.core.entity.item.container.logic.remove
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.npc.logic.systems.npcSpawn
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
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
        entity.create(MoveStep::class).set(1886, 3178)
    }
    then(EntityActions::task)
}

on(ObjectOption, "Leave-area", "Edgeville portal") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity.create(MoveStep::class).set(3082, 3474)
    }
    then(EntityActions::task)
}

on(ObjectOption, "Join-team", "Balance portal") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        dialogue {
            entity statement """
                <col=ff0000>Warning:<col=000000> a game is currently in progress. If you enter the portal,
                you will be added to the game as soon as possible.
            """
            val choice = entity options """
                Enter portal.
                Stay outside.
            """
            if (choice == FIRST) {
                //TODO random walkable position in area
                if (Random.nextBoolean()) {// TODO weighted team calculation
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

on(NpcOption, "Rewards", "Nomad", "Zimberfizz") { ->
    fun EntityActions.task(npc: Int) = queue {
        entity perform OpenInterface(SoulWarsRewards)
    }
    then(EntityActions::task)
}

lateinit var forceMovementMapper: ComponentMapper<ForceMovement>
lateinit var blackboardMapper: ComponentMapper<Blackboard>

// TODO prevent removing soul wars cape

on(ObjectOption, "Pass", "Blue barrier") { ->
    fun EntityActions.enterTeam(objectId: Int) = queue {
        val blackboard = blackboardMapper.get(entity)
        val enter = blackboard.getString("SoulWarsTeam") != "blue"
        val position = if (enter) Position(1879, 3162) else Position(1880, 3162)
        val equipment = entity container ContainerType.EQUIPMENT
        if (enter && equipment[SLOT_CAPE] != null) {
            entity perform Chat("You can't wear a cape into Soul Wars.")// TODO reference
            return@queue
        }
        entity perform Animation(10584)
        forceMovementMapper.create(entity).apply {
            firstPosition = position
            firstDelay = 1
            direction = if (enter) WEST else EAST
        }
        await(Ticks(1))
        val cape = Item(14642, 1)
        entity.create(MoveStep::class).set(position.x, position.y)
        when (ContainerType.EQUIPMENT transform if (enter) equip(cape.type) else remove(cape.type)) {
            is ItemResult.Success -> {
                if (enter) {
                    entity perform EquippedItem(cape)
                } else {
                    entity perform UnequippedItem(cape)
                }
                if(enter) {
                    blackboard["SoulWarsTeam"] = "blue"// TODO this is probably a config
                } else {
                    blackboard.remove("SoulWarsTeam")
                }
                entity perform UpdateAppearance()
            }
            is ItemResult.Issue -> {
            }
        }
    }
    then(EntityActions::enterTeam)
}

on(NpcOption, "Talk-to", "Nomad") { ->
    fun EntityActions.task(npc: Int) = strongQueue {
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
            when (choice) {
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

                    when (choice) {
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