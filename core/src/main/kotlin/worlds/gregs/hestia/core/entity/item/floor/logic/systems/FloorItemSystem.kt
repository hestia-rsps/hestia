package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.forEach
import worlds.gregs.hestia.artemis.nearby
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Position.Companion.localPosition
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public
import worlds.gregs.hestia.core.world.map.model.Chunk.getChunkX
import worlds.gregs.hestia.core.world.map.model.Chunk.getChunkY
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.core.world.map.model.MapConstants
import worlds.gregs.hestia.network.client.encoders.messages.*
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import kotlin.math.max

class FloorItemSystem : FloorItems() {

    private lateinit var privateMapper: ComponentMapper<Private>
    private lateinit var publicMapper: ComponentMapper<Public>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var amountMapper: ComponentMapper<Amount>
    private lateinit var es: EventSystem
    private lateinit var chunkSubscription: EntitySubscription
    private lateinit var itemDefinition: ItemDefinitionSystem
    private val items = mutableMapOf<Int, ArrayList<Int>>()

    override fun initialize() {
        super.initialize()
        chunkSubscription = world.aspectSubscriptionManager.get(Aspect.all(NetworkSession::class, Position::class))
    }

    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)

        //Add to list
        val items = items.getOrPut(toChunkPosition(position)) { ArrayList() }
        items.add(entityId)

        //Combine if another exists on the same point
        if(combinedItems(entityId)) {
            return
        }

        //Send to local players
        sendFloorItem(entityId)
    }

    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)

        //Remove from list
        val items = items[toChunkPosition(position)] ?: return
        if(items.contains(entityId)) {
            items.remove(entityId)

            //Remove from local players
            removeFloorItem(entityId)
        }
    }

    override fun sendFloorItem(entityId: Int) {
        //Send
        forAllLocals(entityId) { playerId ->
            val lastRegion = lastLoadedRegionMapper.get(playerId)
            val name = displayNameMapper.get(playerId).name
            val client = clientIndexMapper.get(playerId).index
            sendFloorItem(playerId, lastRegion, name, client, entityId, true)
        }
    }

    override fun updateFloorItem(entityId: Int, quantity: Int) {
        forAllLocals(entityId) { playerId ->
            val position = positionMapper.get(entityId)
            val type = typeMapper.get(entityId)
            val lastRegion = lastLoadedRegionMapper.get(playerId)
            val amount = amountMapper.get(entityId)
            val positionOffset = positionOffset(position, lastRegion)
            //Update the amount
            val oldAmount = amount.amount
            amount.amount = quantity
            //Send the change
            es.send(playerId, FloorItemUpdate(positionOffset, type.id, oldAmount, quantity))
        }
    }

    override fun removeFloorItem(entityId: Int) {
        forAllLocals(entityId) { playerId ->
            val position = positionMapper.get(entityId)
            val type = typeMapper.get(entityId)
            val lastRegion = lastLoadedRegionMapper.get(playerId)
            val localX = localPosition(position.x, lastRegion.chunkX)
            val localY = localPosition(position.y, lastRegion.chunkY)
            val positionOffset = positionOffset(localX, localY)
            //Update chunk location
            es.send(playerId, ChunkSend(localX shr 3, localY shr 3, position.plane))
            //Removal
            es.send(playerId, FloorItemRemove(type.id, positionOffset))
        }
    }

    /**
     * Helper method; loops all entities with [NetworkSession] & [Position] within radius of [entityId] floor item
     * @param entityId The floor item entity id
     * @param action Action to take for any nearby players found
     */
    internal fun forAllLocals(entityId: Int, action: (playerId: Int) -> Unit) {
        val position = positionMapper.get(entityId)
        chunkSubscription.entities.forEach { playerId ->
            val playerPosition = positionMapper.get(playerId)
            val size = (MapConstants.MAP_SIZES[0] shr 4) / 2
            if (position.plane == playerPosition.plane && position.chunkX in playerPosition.chunkX.nearby(size) && position.chunkY in playerPosition.chunkY.nearby(size)) {
                action.invoke(playerId)
            }
        }
    }

    override fun sendFloorItems(playerId: Int, chunk: Int, clear: Boolean) {
        val items = items[chunk]

        //Clear chunk of existing items
        if (clear) {
            clearFloorItems(playerId, chunk)
        }

        //If chunk has items
        if (!items.isNullOrEmpty()) {
            val name = displayNameMapper.get(playerId).name
            val client = clientIndexMapper.get(playerId).index
            val lastRegion = lastLoadedRegionMapper.get(playerId)

            //Send all items in chunk
            items.forEachIndexed { index, item ->
                sendFloorItem(playerId, lastRegion, name, client, item, index == 0)
            }
        }
    }

    override fun clearFloorItems(playerId: Int, chunk: Int) {
        val position = positionMapper.get(playerId)
        val lastRegion = lastLoadedRegionMapper.get(playerId)
        val localX = localPosition(getChunkX(chunk) shl 3, lastRegion.chunkX)
        val localY = localPosition(getChunkY(chunk) shl 3, lastRegion.chunkY)
        es.send(playerId, ChunkClear(localX shr 3, localY shr 3, position.plane))
    }

    override fun hasItems(chunk: Int): Boolean {
        return items[chunk] != null
    }

    override fun getItems(chunk: Int): List<Int> {
        return items[chunk] ?: emptyList()
    }

    override fun getItems(): List<Int> = items.flatMap { it.value }

    internal fun sendFloorItem(entityId: Int, lastRegion: LastLoadedRegion, name: String?, clientIndex: Int?, item: Int, updateChunk: Boolean) {
        val position = positionMapper.get(item)
        val localX = localPosition(position.x, lastRegion.chunkX)
        val localY = localPosition(position.y, lastRegion.chunkY)
        val positionOffset = positionOffset(localX, localY)

        if (updateChunk) {
            es.send(entityId, ChunkSend(localX shr 3, localY shr 3, position.plane))
        }

        //Gather floor item information
        val type = typeMapper.get(item)
        val amount = amountMapper.get(item)
        val private = privateMapper.get(item)
        val public = publicMapper.get(item)

        //If item is public but entity is owner
        val publicOwner = private == null && public != null && public.owner == clientIndex
        //or item is private and entity is owner
        val privateOwner = name != null && private?.id != null && private.id == name
        //If item is public and entity isn't the owner
        val generalPublic = private?.id == null && public != null && (public.owner == -1 || clientIndex != null && public.owner != clientIndex)

        //Send private/public floor item
        if (publicOwner || privateOwner) {
            es.send(entityId, FloorItemPrivate(positionOffset, type.id, amount.amount))
        } else if (generalPublic) {
            es.send(entityId, FloorItemPublic(public?.owner ?: -1, positionOffset, type.id, amount.amount))
        }
    }

    internal fun combinedItems(entityId: Int): Boolean {
        val position = positionMapper.get(entityId)
        val type = typeMapper.get(entityId)
        val amount = amountMapper.get(entityId)
        val private = privateMapper.get(entityId)
        val public = publicMapper.get(entityId)
        val stackable = itemDefinition.get(type.id).stackable == 1
        //If item is stackable check the designated tile for an item of:
        val combine = if (stackable) items[toChunkPosition(position)]?.firstOrNull {
            it != entityId//Not self
                    && typeMapper.get(it).id == type.id//Same type
                    && positionMapper.get(it).same(position)//Same position
                    && ((public != null && publicMapper.get(it) != null)//Both public
                    || (private?.id != null && privateMapper.get(it)?.id != null && private.id == privateMapper.get(it).id))//Both same private owner
        } else null

        //Combine the two if same
        if (combine != null) {
            val otherPrivate = privateMapper.get(combine)
            val otherPublic = publicMapper.get(combine)
            val otherAmount = amountMapper.get(combine)
            //Combine amounts
            val oldAmount = otherAmount.amount
            otherAmount.amount = amount.amount + otherAmount.amount
            //Combine timeouts
            if (otherPrivate != null && private != null) {
                otherPrivate.timeout = max(otherPrivate.timeout, private.timeout)
            }
            if (otherPublic != null && public != null) {
                otherPublic.timeout = max(otherPublic.timeout, public.timeout)
            }
            //Update the old item
            forAllLocals(combine) { playerId ->
                val lastRegion = lastLoadedRegionMapper.get(playerId)
                val positionOffset = positionOffset(position, lastRegion)
                es.send(playerId, FloorItemUpdate(positionOffset, type.id, oldAmount, otherAmount.amount))
            }
            //Delete the new item
            items[toChunkPosition(position)]!!.remove(entityId)//Remove preemptively so removal of updated item isn't sent
            world.delete(entityId)
        }
        return combine != null
    }

    companion object {
        private fun positionOffset(position: Position, lastRegion: Position): Int {
            val localX = localPosition(position.x, lastRegion.chunkX)
            val localY = localPosition(position.y, lastRegion.chunkY)
            return positionOffset(localX, localY)
        }

        private fun positionOffset(localX: Int, localY: Int): Int {
            val offsetX = localX - ((localX shr 3) shl 3)
            val offsetY = localY - ((localY shr 3) shl 3)
            return (offsetX shl 4) or offsetY
        }
    }
}