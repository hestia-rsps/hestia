package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.perform
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.mob.model.events.MobOption
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.MobOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

class MobOptionHandler : MessageHandlerSystem<MobOptionMessage>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var es: EventSystem
    private lateinit var mobDefinitions: MobDefinitionSystem
    private val logger = LoggerFactory.getLogger(MobOptionHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var typeMapper: ComponentMapper<Type>

    override fun handle(entityId: Int, message: MobOptionMessage) {
        val (run, mobIndex, option) = message
        val viewport = viewportMapper.get(entityId)
        val mobId = viewport.localMobs().getEntity(mobIndex)
        if(mobId != -1) {
            val type = typeMapper.get(mobId)
            val definition = mobDefinitions.get(type.id)
            val option = definition.options.getOrNull(option - 1)
            if(option != null) {
                es.perform(entityId, MobOption(mobId, option, definition.name))//ID NOT TYPE
            } else {
                logger.warn("Invalid mob option ${type.id} $option ${definition.options.toList()}")
            }
        } else {
            logger.warn("Can't find mob index $mobIndex $option")
        }
    }
}