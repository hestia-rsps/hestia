package worlds.gregs.hestia.game.plugins.player.systems.map

import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkChangeSystem
import worlds.gregs.hestia.game.plugins.player.component.Player
import worlds.gregs.hestia.game.plugins.region.systems.RegionSystem
import worlds.gregs.hestia.game.region.MapConstants.MAP_SIZES

class PlayerChunkChangeSystem : EntityChunkChangeSystem(Player::class) {

    private lateinit var map: PlayerChunkMap
    private lateinit var regions: RegionSystem

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        //Change chunk
        map.remove(from, entityId)
        map.add(to, entityId)

        //Preload new regions
        if(to != -1) {
            forNearbyRegions(to shr 14 and 0x3fff, to and 0x3fff) { regionId ->
                regions.load(regionId)
            }
        }
    }

    companion object {
        fun forNearbyRegions(chunkX: Int, chunkY: Int, action: (Int) -> Unit) {
            val mapHash = MAP_SIZES[0] shr 4
            for (regionX in (chunkX - mapHash) / 8..(chunkX + mapHash) / 8) {
                for (regionY in (chunkY - mapHash) / 8..(chunkY + mapHash) / 8) {
                    action(regionY + (regionX shl 8))
                }
            }
        }
    }
}