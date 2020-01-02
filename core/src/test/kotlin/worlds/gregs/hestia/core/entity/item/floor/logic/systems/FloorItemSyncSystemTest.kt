package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.components.ReloadFloorItems

@ExtendWith(MockKExtension::class)
internal class FloorItemSyncSystemTest : MockkGame() {

    private val system = FloorItemSyncSystem()

    @RelaxedMockK
    lateinit var floorItems: FloorItems

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, floorItems)
    }

    override fun start() {
        world.createEntity().edit().add(ReloadFloorItems()).add(Position())
        tick()
    }

    @Test
    fun `Sends floor items and removes component`() {
        //Given
        every { floorItems.sendFloorItems(any(), any(), any()) } answers {}
        //When
        tick()
        //Then
        assertNull(world.getEntity(0).getComponent(LastLoadedRegion::class))
        verify { floorItems.sendFloorItems(0, 0, true) }
    }

}