package worlds.gregs.hestia.artemis

import com.artemis.Component
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.PooledWeaver
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.getComponent
import worlds.gregs.hestia.core.remove

class ObservableTest : GameTest(WorldConfigurationBuilder()) {

    @PooledWeaver
    class Observer : Component() {
        var value = -1
    }

    @Test
    fun test() {
        val entity = world.createEntity()
        entity.edit().add(Observer())
        tick()

        println(entity.getComponent(Observer::class))

        entity.edit().remove(Observer::class)
        tick()

        entity.edit().add(Observer())
        tick()
        println(entity.getComponent(Observer::class))

    }
}