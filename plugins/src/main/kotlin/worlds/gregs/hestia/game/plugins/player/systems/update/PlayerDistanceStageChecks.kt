package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.api.player.Player
import worlds.gregs.hestia.game.plugins.entity.systems.update.stage.EntityStageSystem
import worlds.gregs.hestia.game.plugins.player.component.PlayerViewDistance

class PlayerDistanceStageChecks : PassiveSystem() {

    private lateinit var entityStageSystem: EntityStageSystem
    private lateinit var playerMapper: ComponentMapper<Player>
    private lateinit var playerViewDistanceMapper: ComponentMapper<PlayerViewDistance>

    override fun initialize() {
        super.initialize()
        entityStageSystem.addCheck { player, other ->
            if(playerMapper.has(other)) {
                playerViewDistanceMapper.get(player).distance
            } else {
                null
            }
        }
    }
}