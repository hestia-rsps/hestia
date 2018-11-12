package world.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import world.gregs.hestia.game.Region
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.services.one

class RegionSystem : SubscriptionSystem(Aspect.all(ClientIndex::class).one(Mob::class, Player::class)) {

    private lateinit var mobMapper: ComponentMapper<Mob>
    private lateinit var positionMapper: ComponentMapper<Position>
    val regions = ArrayList<Region>()

    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        var region = regions.firstOrNull { it.regionId == position.regionId }
        if(region == null) {
            region = Region(position.regionId)
            regions.add(region)
        }

        if(mobMapper.has(entityId)) {
            region.mobs.add(entityId)
        } else {
            region.players.add(entityId)
        }
    }

    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        val region = regions.firstOrNull { it.regionId == position.regionId }

        if(mobMapper.has(entityId)) {
            region?.mobs?.remove(entityId)
        } else {
            region?.players?.remove(entityId)
        }
    }
}