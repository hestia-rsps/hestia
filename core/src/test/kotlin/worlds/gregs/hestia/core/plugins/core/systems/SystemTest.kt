package worlds.gregs.hestia.core.plugins.core.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.Wire
import com.artemis.systems.DelayedIteratingSystem
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getSystem

internal class SystemTest : GameTest(WorldConfigurationBuilder().with(TestSystem())) {

    class TestComponent : Resettable()

    @Wire(injectInherited = true)
    class TestSystem : ResettableSystem<TestComponent>(Aspect.all(TestComponent::class)) {

        override lateinit var resettableMapper: ComponentMapper<TestComponent>

        override fun inserted(entityId: Int) {
            super.inserted(entityId)
            println("Inserted $entityId")
        }

        override fun removed(entityId: Int) {
            println("Removed $entityId")
        }
    }

    /*
    ComponentMapper.set(entityId, boolean) is just a helper function for adding and removing an entity component, which does call inserted and removed.
     */
abstract class Resettable : Component() {
    var cooldown = 0f
}

abstract class ResettableSystem<T: Resettable>(aspect: com.artemis.Aspect.Builder) : DelayedIteratingSystem(aspect) {

    abstract var resettableMapper: ComponentMapper<T>

    override fun processDelta(entityId: Int, accumulatedDelta: Float) {
        val resettable = resettableMapper.get(entityId)
        resettable.cooldown -= accumulatedDelta
    }

    override fun getRemainingDelay(entityId: Int): Float {
        val resettable = resettableMapper.get(entityId)
        return resettable.cooldown
    }

    override fun processExpired(entityId: Int) {
        val resettable = resettableMapper.get(entityId)
        if(resettable.cooldown > 0) {
            offerDelay(resettable.cooldown)
        } else {
            resettableMapper.remove(entityId)
        }
    }
}

    @Test
    fun test() {
        val system = world.getSystem(TestSystem::class)
        world.createEntity().edit().add(TestComponent().apply { cooldown = 0.6f * 4 })
        tick()
        repeat(5) {
            tick()
        }
    }

    override fun tick() {
        println("Tick")
        world.setDelta(0.6f)//600ms
        super.tick()
    }
}