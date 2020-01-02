package worlds.gregs.hestia.core.display.update.logic.sync

import com.artemis.ComponentMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import worlds.gregs.hestia.GameConstants.PLAYERS_LIMIT
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.ParallelSystem
import worlds.gregs.hestia.artemis.bag.BitBag
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.logic.sync.ViewportSystem.Companion.MAXIMUM_LOCAL_ENTITIES
import worlds.gregs.hestia.core.entity.entity.model.components.Created
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.api.container.EntityMap
import worlds.gregs.hestia.core.world.map.api.container.MobMap
import worlds.gregs.hestia.core.world.map.api.container.PlayerMap
import worlds.gregs.hestia.core.world.map.logic.Spiral

/**
 * ViewportSystem
 * Handles which entities should be displayed within the view of the player up to [MAXIMUM_LOCAL_ENTITIES]
 * Could be optimised by being reactive
 */
class ViewportSystem : ParallelSystem(Aspect.all(Viewport::class, Position::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var createdMapper: ComponentMapper<Created>
    private lateinit var mobMap: MobMap
    private lateinit var playerMap: PlayerMap
    private val bags = arrayOfNulls<BitBag>(PLAYERS_LIMIT)//Concurrency; could be improved

    override fun inserted(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val clientIndex = clientIndexMapper.get(entityId).index
        createdMapper.create(entityId)
        val locals = viewport.localPlayers()
        locals.set(entityId, clientIndex)
        bags[clientIndex] = BitBag(MAXIMUM_LOCAL_ENTITIES)
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId).index
        bags[clientIndex] = null
    }

    override fun processAsync(entityId: Int) = GlobalScope.async {
        val position = positionMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)
        val players = viewport.localPlayers()
        val clientIndex = clientIndexMapper.get(entityId).index
        val bag = bags[clientIndex]!!

        processEntities(bag, playerMap, position, players, entityId)
        processEntities(bag, mobMap, position, viewport.localMobs())
    }

    private fun processEntities(bag: BitBag, map: EntityMap, position: Position, entities: EntitySyncBag, entityId: Int? = null) {
        //Skip the fast search if we know advanced is needed (could add a tolerance to tweak performance)
        var accurate = entities.size == MAXIMUM_LOCAL_ENTITIES

        //Bag rough approximate entities
        if (!accurate) {
            map.get(bag, position, radius = DEFAULT_VIEW_DISTANCE)
            accurate = bag.size() >= MAXIMUM_LOCAL_ENTITIES
            if (accurate) {
                bag.clear()
            }
        }

        //If too many entities then use a more accurate sort
        if (accurate) {
            spiral(bag, map, position)
        }

        //Removal all locals not meant to be in the viewport
        //Could be optimised - current & next arrays, checking both to see if add/remove. Map would have to be LinkedHashMap<Index, Entity> in the same order-by-distance as bag
        entities.forEachIndexed { index, entity ->
            if (entity == null) {
                return@forEachIndexed
            }
            //If not meant to be in viewport (excluding self)
            if ((entityId == null || entity != entityId) && !bag.contains(entity)) {
                //Remove
                if (!entities.needsRemoval(entity)) {
                    entities.remove(entity, index)
                }
            }
        }

        var spaces = MAXIMUM_LOCAL_ENTITIES - (entities.size - entities.removals() + entities.insertions())
        //For all supposed to be in view
        for (i in 0 until bag.size()) {
            //Stop if no local spaces
            if (spaces <= 0) {
                break
            }
            val entity = bag.data[i]
            //Ignore self
            if (entityId != null && entity == entityId) {
                continue
            }
            //If not already in/going to be in view
            if (!entities.needsInsert(entity)) {
                val clientIndex = clientIndexMapper.get(entity).index
                if (!entities.containsIndex(clientIndex)) {
                    //Add
                    entities.insert(entity, clientIndex)
                    spaces--
                }
            }
        }
        //Clean up
        bag.clear()
    }

    private fun spiral(bag: BitBag, map: EntityMap, position: Position) {
        //Spiral clockwise outwards
        Spiral.spiral(DEFAULT_VIEW_DISTANCE).forEach { point ->
            //For each position
            val hash = Position.hash24Bit(position.x + point.first, position.y + point.second, position.plane)
            //For all entities
            map.getTile(hash)?.forEach { entity ->
                //Add
                bag.add(entity)//Note: We don't use addAll here as the bag size is fixed so there's no performance benefit
                //And break if reached maximum
                if (bag.size() >= MAXIMUM_LOCAL_ENTITIES) {
                    return
                }
            }
        }
    }

    companion object {
        const val DEFAULT_VIEW_DISTANCE = 15
        const val MAXIMUM_LOCAL_ENTITIES = 255
    }
}
