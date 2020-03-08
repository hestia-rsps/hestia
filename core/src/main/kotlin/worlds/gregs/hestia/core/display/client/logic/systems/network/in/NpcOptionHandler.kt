package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.ActionContext
import worlds.gregs.hestia.core.action.logic.systems.NpcOptionSystem
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.NpcOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.NpcDefinitionSystem

class NpcOptionHandler : MessageHandlerSystem<NpcOptionMessage>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var npcDefinitions: NpcDefinitionSystem
    private val logger = LoggerFactory.getLogger(NpcOptionHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var options: NpcOptionSystem
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>

    override fun handle(entityId: Int, message: NpcOptionMessage) {
        val (run, npcIndex, optionId) = message
        val viewport = viewportMapper.get(entityId)
        val npcId = viewport.localNpcs().getEntity(npcIndex)
        if(npcId != -1) {
            val type = typeMapper.get(npcId)
            val definition = npcDefinitions.get(type.id)
            val option = definition.options.getOrNull(optionId - 1)
            if(option != null) {
                val action = options.get(option, type.id) ?: return logger.warn("Invalid npc option ${type.id} $option")
                val actionContext = actionContextMapper.get(entityId)
                action.invoke(actionContext, npcIndex)
            } else {
                logger.warn("Invalid npc option ${type.id} $option ${definition.options.toList()}")
            }
        } else {
            logger.warn("Can't find npc index $npcIndex $optionId")
        }
    }
}