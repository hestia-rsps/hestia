package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public

@ExtendWith(MockKExtension::class)
internal class PrivateSystemTest : MockkGame() {

    @SpyK
    private var system = PublicSystem()

    @RelaxedMockK
    lateinit var floorItems: FloorItems

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, floorItems)
    }

    @Test
    fun `Timeout is decremented one each tick`() {
        //Given
        val public = Public(0, 10)
        world.createEntity().edit().add(public)
        tick()
        //When
        tick()
        //Then
        assertEquals(8, public.timeout)//Tick on insert also counts - to change, simply +1 upon insertion.
    }

    @Test
    fun `Timeout complete no public is deleted`() {
        //Given
        val public = Public(0, 1)
        val entity = world.createEntity()
        entity.edit().add(public)
        tick()
        //When
        tick()
        //Then
        assertEquals(0, public.timeout)
        assertEquals(false, entity.isActive)
    }
}