package worlds.gregs.hestia.game.plugins.widget.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.Widget
import worlds.gregs.hestia.game.plugins.widget.systems.frame.getId
import worlds.gregs.hestia.network.game.out.InterfaceClose
import worlds.gregs.hestia.network.game.out.InterfaceOpen
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

    override fun getWindow(entityId: Int): Int? {
        return if(gameFrameMapper.has(entityId)) {
            gameFrameMapper.get(entityId).getId()
        } else {
            null
        }
    }


    override fun getIndex(entityId: Int): Int {
        return if(gameFrameMapper.has(entityId)) {
            getIndex(gameFrameMapper.get(entityId).resizable)
        } else {
            0
        }
    }

    open fun getIndex(resizable: Boolean): Int {
        return 0
    }

    override fun open(entityId: Int) {
        val window = getWindow(entityId)
        if(window != null) {
            val index = getIndex(entityId)
            val id = getId(entityId)
            es.send(entityId, InterfaceOpen(frame, window, index, id))
        }
    }

    override fun close(entityId: Int) {
        val window = getWindow(entityId)
        if(window != null) {
            val index = getIndex(entityId)
            es.send(entityId, InterfaceClose(window, index))
        }
    }
}