package worlds.gregs.hestia.game.plugins.client.systems.update.bases.update

import com.artemis.Component
import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.api.update.Sync
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.services.Aspect
import java.util.*
import kotlin.reflect.KClass

abstract class BaseEntitySyncSystem(vararg classes: KClass<out Component>) : Sync(Aspect.all(NetworkSession::class, Viewport::class, *classes)) {

    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    internal lateinit var packet: PacketBuilder
    internal var total = 0//Total number of entities
    private var added = 0//Number of new local entities added
    internal lateinit var iterator: MutableIterator<Int>

    open val newEntitiesPerCycle = 20
    open val maximumEntities = 255

    /**
     * Get the packet to write too
     */
    abstract fun packet(entityId: Int): PacketBuilder

    open fun start() {
        packet.startBitAccess()
    }

    open fun middle() {
    }

    /**
     * Get local entities
     */
    abstract fun locals(entityId: Int): ArrayList<Int>

    /**
     * Get global entities
     */
    abstract fun globals(entityId: Int): ArrayList<Int>

    /**
     * Calculate the number of local entities after some have been removed
     */
    abstract fun count(locals: List<Int>, stages: Map<Int, DisplayFlag>): Int

    /**
     * Skip the processing and write an empty packet
     */
    override fun skip(locals: List<Int>, globals: List<Int>) {}

    override fun process(entityId: Int) {
        total = 0//Reset
        added = 0
        packet = packet(entityId)

        //Gather all the update data
        val locals = locals(entityId)
        val globals = globals(entityId)
        val stages = entityUpdatesMapper.get(entityId).map
        val updates = entityUpdatesMapper.get(entityId).list

        //If no updates are required then skip the loops
        if(stages.isEmpty() && updates.isEmpty()) {
            skip(locals, globals)
            return
        }

        //Get the initial local count
        total = locals.size

        //Pre loop stuff
        start()

        //For each local
        iterator = locals.iterator()
        while (iterator.hasNext()) {
            val local = iterator.next()
            //Process local
            local(entityId, local, stages[local], updates.contains(local))
        }

        //Half way
        middle()

        //Get the new total of locals
        val count = count(locals, stages)

        //For each global
        iterator = globals.iterator()
        while (iterator.hasNext()) {
            val global = iterator.next()

            //Stop if limit reached
            if (!limitCheck(count)) {
                break
            }

            //Keep note of total
            total++

            //Get the type
            val type = stages[global]
            val update = updates.contains(global)

            //Keep note of number of entities added
            if (type == DisplayFlag.ADD) {
                added++
            }

            //Process global
            global(entityId, global, type, update)
        }
    }

    private fun limitCheck(count: Int): Boolean {
        return when {
            //Viewport cap
            added + count >= maximumEntities -> false
            //Number of players added has to be capped due to maximum packet size
            added >= newEntitiesPerCycle -> false
            else -> true
        }
    }
}