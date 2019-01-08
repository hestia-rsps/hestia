package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.services.getSystem
import worlds.gregs.hestia.tools.InterfaceTester

internal class CloseInterfaceHandlerTest : InterfaceTester(WorldConfigurationBuilder().with(CloseInterfaceHandler())) {

    private lateinit var entity: Entity

    override fun start() {
        entity = world.createEntity()
        entity.edit().add(mock<ScreenWidget>())
    }

    @Test
    fun `Close an open screen widget`() {
        //Given
        open(ScreenWidget::class)
        //When
        sendClose()
        tick()
        //Then
        assertClosed(1, 0)
    }

    @Test
    fun `Close another entities`() {
        //Given
        open(ScreenWidget::class)
        //When
        sendClose(1)
        //Then
        assertClosed(0, 0)
    }

    private fun sendClose(entityId: Int = 0) {
        val system = world.getSystem(CloseInterfaceHandler::class)
        val packet = Packet.Builder().build()
        system.handle(entityId, packet, packet.length)
    }
}
