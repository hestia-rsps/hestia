package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.ActionContext
import worlds.gregs.hestia.core.action.logic.systems.NpcOptionSystem
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.task.model.events.StartTask
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.NpcOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.NpcDefinitionSystem

class NpcOptionHandler : MessageHandlerSystem<NpcOptionMessage>(), InteractionHandler {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var npcDefinitions: NpcDefinitionSystem
    private val logger = LoggerFactory.getLogger(NpcOptionHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private lateinit var options: NpcOptionSystem
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>
    private lateinit var positionMapper: ComponentMapper<Position>
    override lateinit var stepsMapper: ComponentMapper<Steps>
    override lateinit var followingMapper: ComponentMapper<Following>
    override lateinit var shiftMapper: ComponentMapper<Shift>
    override lateinit var es: EventSystem

    override fun handle(entityId: Int, message: NpcOptionMessage) {
        val (run, npcIndex, optionId) = message
        val viewport = viewportMapper.get(entityId)
        val npcId = viewport.localNpcs().getEntity(npcIndex)
        if (npcId != -1) {
            val type = typeMapper.get(npcId)
            val definition = npcDefinitions.get(type.id)
            val option = definition.options.getOrNull(optionId - 1)
            if (option != null) {
                val action = options.get(option, type.id) ?: return logger.warn("Invalid npc option ${type.id} $option")
                es.perform(entityId, StartTask(InactiveTask(1, interact(entityId, npcId, true) {
                    es.perform(entityId, Face(positionMapper.get(npcId), sizeMapper.get(npcId)))
                    val actionContext = actionContextMapper.get(entityId)
                    action.invoke(actionContext, npcId)
                })))
            } else {
                logger.warn("Invalid npc option ${type.id} $option ${definition.options.toList()}")
            }
        } else {
            logger.warn("Can't find npc index $npcIndex $optionId")
        }
    }
}