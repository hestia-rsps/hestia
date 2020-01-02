package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems

@ExtendWith(MockKExtension::class)
internal class PublicSystemTest: MockkGame() {

    @SpyK
    private var system = PrivateSystem()

    @RelaxedMockK
    lateinit var floorItems: FloorItems

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, floorItems)
    }

}