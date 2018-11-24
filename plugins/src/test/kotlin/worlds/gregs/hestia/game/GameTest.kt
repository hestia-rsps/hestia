package worlds.gregs.hestia.game

import com.artemis.Entity
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.mob.archetypes.MobFactory
import worlds.gregs.hestia.game.player.archetypes.PlayerFactory
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.events.CreateMob
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.game.mob.systems.MobCreation
import worlds.gregs.hestia.game.player.systems.PlayerCreation
import worlds.gregs.hestia.services.getComponent
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach
import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.game.archetypes.EntityFactory

abstract class GameTest(private val config: WorldConfigurationBuilder) {

    lateinit var w: World
    lateinit var es: EventSystem
    lateinit var pc: PlayerCreation
    lateinit var mc: MobCreation

    @BeforeEach
    fun setUp() {
        es = EventSystem()
        pc = PlayerCreation()
        mc = MobCreation()
        config.with(es, pc, mc)
        val config = config.build()
        w = World(config)
        EntityFactory.init(w)
        EntityFactory.add(PlayerFactory())
        EntityFactory.add(MobFactory())
    }

    fun tick() {
        w.process()
    }

    fun fakeMob(id: Int = 0): Entity {
        return w.getEntity(mc.create(CreateMob(id, 0, 0)))
    }

    fun fakePlayer(x: Int = 0, y: Int = 0, name: String = "Dummy"): Entity {
        val entityId = pc.create(CreatePlayer(Session(), name))
        val player = w.getEntity(entityId)
        val position = player.getComponent(Position::class)!!
        position.x = x
        position.y = y
        return player
    }

}