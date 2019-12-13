package worlds.gregs.hestia.core.entity.player.logic.systems.chunk

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.world.region.api.Region
import worlds.gregs.hestia.core.entity.player.model.events.PlayerChunkChanged
import worlds.gregs.hestia.core.world.map.model.Chunk.getChunkX
import worlds.gregs.hestia.core.world.map.model.Chunk.getChunkY
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map.PlayerChunkMap
import worlds.gregs.hestia.core.world.map.model.MapConstants.MAP_SIZES

@Wire(failOnNull = false)
class PlayerChunkSystem : PassiveSystem() {

    private lateinit var map: PlayerChunkMap
    private var region: Region? = null

    @Subscribe
    private fun event(event: PlayerChunkChanged) {
        change(event.entityId, event.from, event.to)
    }

    private fun change(entityId: Int, from: Int?, to: Int?) {
        //Change chunk
        if(from != null) {
            map.remove(from, entityId)
        }
        if(to != null) {
            map.add(to, entityId)


            //Preload new regions
            forNearbyRegions(getChunkX(to), getChunkY(to)) { regionId ->
                region?.load(regionId)
            }
        }
    }

    companion object {
        private fun forNearbyRegions(chunkX: Int, chunkY: Int, action: (Int) -> Unit) {
            val mapHash = MAP_SIZES[0] shr 4
            for (regionX in (chunkX - mapHash) / 8..(chunkX + mapHash) / 8) {
                for (regionY in (chunkY - mapHash) / 8..(chunkY + mapHash) / 8) {
                    action(regionY + (regionX shl 8))
                }
            }
        }
    }
}