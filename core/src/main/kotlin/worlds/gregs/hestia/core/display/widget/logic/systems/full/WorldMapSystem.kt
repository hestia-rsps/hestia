package worlds.gregs.hestia.core.display.widget.logic.systems.full

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.widget.api.GameFrame
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.display.widget.model.components.full.WorldMap
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFullScreen
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.getId
import worlds.gregs.hestia.network.client.encoders.messages.ConfigGlobal
import worlds.gregs.hestia.network.client.encoders.messages.WidgetWindowsPane
import worlds.gregs.hestia.artemis.send

class WorldMapSystem : BaseFullScreen(WorldMap::class) {

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        val position = positionMapper.get(entityId)
        val posHash = position.x shl 14 or position.y
        es.send(entityId, ConfigGlobal(622, posHash))
        es.send(entityId, ConfigGlobal(674, posHash))
    }

    override fun close(entityId: Int) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            es.send(entityId, WidgetWindowsPane(gameFrame.getId(), 2))
        }
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        if(componentId == 44) {
            //Close button
        }
        println("Click world map $componentId")
    }

    companion object {
        private const val ID = 755
    }
}