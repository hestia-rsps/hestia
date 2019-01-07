package worlds.gregs.hestia.game

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.WorldConfigurationBuilder
import com.artemis.WorldConfigurationBuilder.Priority.LOWEST
import com.artemis.systems.IteratingSystem
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getSystem

internal class ComponentTest : GameTest(WorldConfigurationBuilder().with(LOWEST, CompSystem())) {
    internal class Comp : Component() {

    }

    internal class CompSystem : IteratingSystem(Aspect.all(Comp::class)) {
        override fun process(entityId: Int) {

        }

        private lateinit var compMapper: ComponentMapper<Comp>

        override fun inserted(entityId: Int) {
            println("Inserted $entityId")
        }

        override fun removed(entityId: Int) {
            val comp = compMapper.get(entityId)
            println("Removed $comp")
        }

        fun remove(entityId: Int) {
            compMapper.remove(entityId)
        }
    }

    @Test
    fun test() {
        val entity = world.createEntity()
        entity.edit().add(Comp())
        tick()
        val sys = world.getSystem(CompSystem::class)
        sys.remove(entity.id)
        tick()
    }
}