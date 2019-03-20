package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.packet.PacketBuilder

/**
 * Sends a custom region of the map to load
 * @param entity The players entity id used for [login]
 * @param chunkX The x coordinate of the chunk to start loading
 * @param chunkY The y coordinate of the chunk to start loading
 * @param forceReload Whether to reload existing chunks
 * @param mapSize The map size in tiles
 * @param mapHash The radius of chunks to load
 * @param login If first map load; include all entity positions
 * @param chunks List of chunk position hashes
 * @param chunkCount Total dynamic chunk count
 */
data class MapRegionDynamic(val entity: Int, val chunkX: Int, val chunkY: Int, val forceReload: Boolean, val mapSize: Int, val mapHash: Int, val login: (PacketBuilder.(Int) -> Unit)?, val chunks: List<Int?>, val chunkCount: Int) : Message