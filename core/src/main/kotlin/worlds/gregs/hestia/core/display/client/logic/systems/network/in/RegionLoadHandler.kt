package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.floor.model.components.ReloadFloorItems
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.RegionLoaded

@Wire(failOnNull = false)
class RegionLoadHandler : MessageHandlerSystem<RegionLoaded>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>
    private lateinit var reloadFloorItemsMapper: ComponentMapper<ReloadFloorItems>

    override fun handle(entityId: Int, message: RegionLoaded) {
        val lastRegion = lastLoadedRegionMapper.get(entityId)
        val position = positionMapper.get(entityId)
        //Set last loaded region
        lastRegion.set(position)
        //Reload floor items
        reloadFloorItemsMapper.create(entityId)
    }

}