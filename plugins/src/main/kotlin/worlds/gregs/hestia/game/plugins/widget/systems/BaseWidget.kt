package worlds.gregs.hestia.game.plugins.widget.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.Widget
import worlds.gregs.hestia.game.plugins.widget.systems.frame.getId
import worlds.gregs.hestia.network.out.InterfaceClose
import worlds.gregs.hestia.network.out.InterfaceOpen
import worlds.gregs.hestia.services.send
import kotlin.reflect.KClass

abstract class BaseWidget(component: KClass<out Component>) : Widget(component) {

    internal lateinit var es: EventSystem
    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>

    override fun inserted(entityId: Int) {
        open(entityId)
    }

    override fun removed(entityId: Int) {
        close(entityId)
    }

    override fun open(entityId: Int) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            es.send(entityId, InterfaceOpen(frame, gameFrame.getId(), getIndex(gameFrame.resizable), getId(entityId)))
        }
    }

    override fun close(entityId: Int) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            es.send(entityId, InterfaceClose(gameFrame.getId(), getIndex(gameFrame.resizable)))
        }
    }
}