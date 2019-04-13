package worlds.gregs.hestia.game

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.WorldConfigurationBuilder
import com.artemis.WorldConfigurationBuilder.Priority.LOWEST
import com.artemis.annotations.DelayedComponentRemoval
import com.artemis.systems.IteratingSystem
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect

internal class ComponentTest : GameTest(WorldConfigurationBuilder().with(LOWEST, CompSystem(), Loop())) {
    internal class Comp : Component()

    @DelayedComponentRemoval
    internal class LateComp : Component()

    internal class Loop : IteratingSystem(Aspect.exclude(Comp::class, LateComp::class)) {

        private lateinit var lateCompMapper: ComponentMapper<LateComp>
        private lateinit var compMapper: ComponentMapper<Comp>

        override fun process(entityId: Int) {
            println("Process ${compMapper.has(1)} ${lateCompMapper.has(1)}")
        }

    }
    internal class CompSystem : SubscriptionSystem(Aspect.one(Comp::class, LateComp::class)) {


        private lateinit var lateCompMapper: ComponentMapper<LateComp>
        private lateinit var compMapper: ComponentMapper<Comp>

        override fun inserted(entityId: Int) {
            println("Inserted $entityId")
        }

        override fun removed(entityId: Int) {
            val comp = compMapper.get(entityId)
            val lateComp = lateCompMapper.get(entityId)
            println("Removed $comp $lateComp")
        }

    }

    @Test
    fun test() {
        world.createEntity()
        val entity = world.createEntity()
        entity.edit().add(Comp()).add(LateComp())
        tick()
        world.delete(1)
        tick()
        tick()
    }
}