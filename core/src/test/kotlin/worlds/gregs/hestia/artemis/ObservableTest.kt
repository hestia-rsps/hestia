package worlds.gregs.hestia.artemis

import com.artemis.Component
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.PooledWeaver
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.GameTest

class ObservableTest : GameTest(WorldConfigurationBuilder()) {

    @PooledWeaver
    data class Observer(var value: Int = -1) : Component()

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