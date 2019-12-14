package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.widget.model.components.ScreenWidget
import worlds.gregs.hestia.core.display.widget.logic.systems.screen.CustomScreenWidgetSystem
import worlds.gregs.hestia.network.client.decoders.messages.ScreenClose
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.tools.InterfaceTester

internal class CloseWidgetOptionHandlerTest : InterfaceTester(WorldConfigurationBuilder().with(ScreenCloseHandler())) {

    private lateinit var entity: Entity

    override fun start() {
        entity = world.createEntity()
        entity.edit().add(mock<ScreenWidget>())
    }

    @Test
    fun `Close an open screen widget`() {
        //Given
        open(CustomScreenWidgetSystem::class)
        //When
        sendClose()
        tick()
        //Then
        assertClosed(1, 0)
    }

    @Test
    fun `Close another entities`() {
        //Given
        open(CustomScreenWidgetSystem::class)
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
