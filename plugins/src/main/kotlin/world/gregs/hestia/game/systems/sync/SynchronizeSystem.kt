package world.gregs.hestia.game.systems.sync

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.movement.Mobile
import world.gregs.hestia.game.component.movement.Moving
import world.gregs.hestia.game.component.movement.Run
import world.gregs.hestia.game.component.movement.Walk
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.map.PlayerViewDistance
import world.gregs.hestia.game.component.map.MobViewDistance
import world.gregs.hestia.game.component.update.Created
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.game.update.UpdateStage

abstract class SynchronizeSystem(aspect: com.artemis.Aspect.Builder) : IteratingSystem(aspect) {

    internal lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var playerViewDistanceMapper: ComponentMapper<PlayerViewDistance>
    private lateinit var mobViewDistanceMapper: ComponentMapper<MobViewDistance>
    private lateinit var createdMapper: ComponentMapper<Created>

    //Stage checks
    internal lateinit var mobileMapper: ComponentMapper<Mobile>
    internal lateinit var walkMapper: ComponentMapper<Walk>
    internal lateinit var runMapper: ComponentMapper<Run>
    internal lateinit var movingMapper: ComponentMapper<Moving>
    internal lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var mobMapper: ComponentMapper<Mob>

    internal lateinit var flags: List<FlagType>

    abstract fun getLocals(entityId: Int, viewport: Viewport): MutableList<Int>

    abstract fun getGlobals(entityId: Int, viewport: Viewport): List<Int>

    open fun local(entityId: Int, local: Int, type: UpdateStage, update: Boolean, iterator: MutableIterator<Int>) {
    }

    open fun localCheck(entityId: Int, local: Int): Boolean {
        return true
    }

    open fun global(entityId: Int, global: Int, type: UpdateStage) {
    }

    open fun globalCheck(entityId: Int, global: Int): Boolean {
        return true
    }

    open fun begin(entityId: Int, locals: List<Int>) {}

    open fun middle(entityId: Int, globals: List<Int>) {}

    open fun end(entityId: Int) {}


    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)

        val locals = getLocals(entityId, viewport)
        val globals = getGlobals(entityId, viewport)

        begin(entityId, locals)

        //Local entities
        val localIterator = locals.iterator()
        while (localIterator.hasNext()) {
            val localId = localIterator.next()

            if (!localCheck(entityId, localId)) {
                break
            }

            val update = flags.any { t -> t.subscription.entities.contains(localId) }
            val type = getLocalStage(entityId, localId, update)
            local(entityId, localId, type, update, localIterator)
        }

        middle(entityId, globals)

        //Global entities
        val globalIterator = globals.iterator()
        while (globalIterator.hasNext()) {
            val entityIndex = globalIterator.next()

            if (!globalCheck(entityId, entityIndex)) {
                break
            }

            val type = getGlobalStage(entityId, entityIndex)
            global(entityId, entityIndex, type)
        }

        end(entityId)
    }

    private fun getLocalStage(entityId: Int, local: Int, update: Boolean): UpdateStage {
        return when {
            !world.entityManager.isActive(local) /* || local.hasFinished() */ || !withinDistance(entityId, local) -> UpdateStage.REMOVE
            movingMapper.has(local) -> UpdateStage.MOVE
            runMapper.has(local) -> UpdateStage.RUNNING
            walkMapper.has(local) -> UpdateStage.WALKING
            update -> UpdateStage.UPDATE
            else -> UpdateStage.SKIP
        }
    }

    private fun getGlobalStage(entityId: Int, global: Int): UpdateStage {
        return when {
            !world.entityManager.isActive(global) -> UpdateStage.SKIP
            /* !global.hasFinished() && */withinDistance(entityId, global) -> UpdateStage.ADD
            mobileMapper.has(global) && mobileMapper.get(global).lastPlane != positionMapper.get(global)?.plane -> UpdateStage.HEIGHT
            movingMapper.has(global) -> UpdateStage.MOVE
            else -> UpdateStage.SKIP
        }
    }

    internal fun update(entityId: Int, local: Int, data: Packet.Builder, player: Boolean, added: Boolean) {
        val created = added || createdMapper.has(entityId)
        val types = flags.filter { t -> (created && t.added) || t.subscription.entities.contains(local) }
        if (types.isEmpty()) {
            return
        }
        var maskData = 0
        types.forEach {
            maskData = maskData or it.mask
        }

        if (maskData >= 256) {
            maskData = maskData or 0x80
        }
        if (maskData >= 65536) {
            maskData = maskData or if (player) 0x800 else 0x8000
        }

        data.writeByte(maskData)

        if (maskData >= 256) {
            data.writeByte(maskData shr 8)
        }
        if (maskData >= 65536) {
            data.writeByte(maskData shr 16)
        }

        types.forEach {
            it.unit.encode(data, entityId, local)
        }

        createdMapper.remove(entityId)
    }

    private fun withinDistance(entityId: Int, otherId: Int): Boolean {
        val pos = if (positionMapper.has(entityId)) positionMapper.get(entityId) else return false
        val o = if (positionMapper.has(otherId)) positionMapper.get(otherId) else return false
        val distance = if (mobMapper.has(otherId)) {
            mobViewDistanceMapper.get(entityId)?.distance
        } else {
            playerViewDistanceMapper.get(entityId)?.distance
        } ?: ViewDistanceSystem.DEFAULT_VIEW_DISTANCE
        return pos.withinDistance(o, distance)
    }

    internal fun create(mask: Int, aspect: com.artemis.Aspect.Builder, unit: UpdateEncoder, added: Boolean = false): FlagType {
        return FlagType(mask, world.aspectSubscriptionManager.get(aspect), unit, added)
    }

    internal data class FlagType(val mask: Int, val subscription: EntitySubscription, val unit: UpdateEncoder, val added: Boolean)
}