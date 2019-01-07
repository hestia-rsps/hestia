package worlds.gregs.hestia.game.plugins.widget.systems.full

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.game.plugins.widget.components.full.WorldMap
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFullScreen
import worlds.gregs.hestia.game.plugins.widget.systems.frame.getId
import worlds.gregs.hestia.network.out.GlobalConfig
import worlds.gregs.hestia.network.out.WindowsPane
import worlds.gregs.hestia.services.send

class WorldMapSystem : BaseFullScreen(WorldMap::class) {
    override var id = 755

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun open(entityId: Int) {
        super.open(entityId)
        val position = positionMapper.get(entityId)
        val posHash = position.x shl 14 or position.y
        es.send(entityId, GlobalConfig(622, posHash))
        es.send(entityId, GlobalConfig(674, posHash))
    }

    override fun close(entityId: Int) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            es.send(entityId, WindowsPane(gameFrame.getId(), 2))
        }
    }

    override fun getIndex(resizable: Boolean): Int {
        return 0
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
        if(componentId == 44) {
            //Close button
        }
        println("Click world map $componentId")
    }

}