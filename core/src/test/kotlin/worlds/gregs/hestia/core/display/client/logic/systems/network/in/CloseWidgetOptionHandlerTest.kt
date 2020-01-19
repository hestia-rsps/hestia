package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.display.interfaces.model.components.InterfaceRelationships
import worlds.gregs.hestia.network.client.decoders.messages.ScreenClose
import worlds.gregs.hestia.tools.InterfaceTester

internal class CloseWidgetOptionHandlerTest : InterfaceTester(WorldConfigurationBuilder().with(ScreenCloseHandler())) {

    private lateinit var entity: Entity

    override fun start() {
        entity = world.createEntity()
        entity.edit().add(InterfaceRelationships())
    }

    @Test
    fun `Close an open screen widget`() {
        //Given
        open(750)
        //When
        sendClose()
        tick()
        //Then
        assertClosed(1, 0)
    }

    @Test
    fun `Close another entities`() {
        //Given
        open(750)
        //When
        sendClose(1)
        //Then
        assertClosed(0, 0)
    }

    private fun sendClose(entityId: Int = 0) {
        val system = world.getSystem(ScreenCloseHandler::class)
        system.handle(entityId, ScreenClose())
    }
}
