package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.dialogue.model.type.ItemBox
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ObjBox
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSprite
import worlds.gregs.hestia.network.client.encoders.messages.Script

@ExtendWith(MockKExtension::class)
internal class ItemBoxSystemTest : MockkGame() {

    @SpyK
    private var system = ItemBoxSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system)
    }

    @Test
    fun `Send item box`() {
        //When
        es.perform(0, ItemBox("text", 9009, 650, 10))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, ObjBox)
            es.send(0, Script(3449, 9009, 650))
            es.send(0, InterfaceSprite(ObjBox, 3, 10))
            es.send(0, InterfaceComponentText(ObjBox, 1, "text"))
        }
    }

    @Test
    fun `Send item box no sprite`() {
        //When
        es.perform(0, ItemBox("text", 9009, 650, null))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, ObjBox)
            es.send(0, Script(3449, 9009, 650))
            es.send(0, InterfaceComponentText(ObjBox, 1, "text"))
        }
        verify(exactly = 0) {
            es.send(0, InterfaceSprite(ObjBox, 3, 0))
            es.send(0, InterfaceSprite(ObjBox, 3, -1))
        }
    }
}