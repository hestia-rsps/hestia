package worlds.gregs.hestia.game.plugins.widget.systems.full

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.game.plugins.widget.components.full.WorldMap
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFullScreen
import worlds.gregs.hestia.game.plugins.widget.systems.frame.getId
import worlds.gregs.hestia.network.game.encoders.messages.ConfigGlobal
import worlds.gregs.hestia.network.game.encoders.messages.WidgetWindowsPane
import worlds.gregs.hestia.services.send

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

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        if(componentId == 44) {
            //Close button
        }
        println("Click world map $componentId")
    }

    companion object {
        private const val ID = 755
    }
}