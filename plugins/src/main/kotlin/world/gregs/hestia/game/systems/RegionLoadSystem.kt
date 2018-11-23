package world.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.Renderable
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.component.map.LastLoadedRegion
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.map.MapConstants.MAP_SIZES
import world.gregs.hestia.game.events.UpdateMapRegion

/**
 * MovementFaceSystem
 * Turns entity to the correct direction when moving
 */
class RegionLoadSystem : IteratingSystem(Aspect.all(Renderable::class, NetworkSession::class, Player::class)) {

    private lateinit var es: EventSystem
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)

        if(needsMapUpdate(entityId, position.chunkX, position.chunkY)) {
            val lastLoadedRegion = lastLoadedRegionMapper.get(entityId)
            lastLoadedRegion.set(position)

            //Load map objects & clipping

            //Send client map update
            es.dispatch(UpdateMapRegion(entityId, false))
        }
    }

    private fun needsMapUpdate(entityId: Int, chunkX: Int, chunkY: Int): Boolean {
        val lastRegion = lastLoadedRegionMapper.get(entityId)
        val size = (MAP_SIZES[0] shr 4) - 1
        return Math.abs(lastRegion.chunkX - chunkX) >= size || Math.abs(lastRegion.chunkY - chunkY) >= size
    }
}