package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.dialogue.model.type.Statement
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp2
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.update.model.components.DisplayName

@ExtendWith(MockKExtension::class)
internal class StatementSystemTest : MockkGame() {

    @SpyK
    private var system = StatementSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system)
    }

    @Test
    fun `Send statement`() {
        //Given
        world.createEntity().edit().add(DisplayName().apply { name = "name" })
        //When
        es.perform(0, Statement(listOf("First", "Second"), `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Message2)
            es.send(0, InterfaceComponentText(Message2, 1, "First"))
            es.send(0, InterfaceComponentText(Message2, 2, "Second"))
        }
    }

    @Test
    fun `Send statement no continue`() {
        //Given
        world.createEntity().edit().add(DisplayName().apply { name = "name" })
        //When
        es.perform(0, Statement(listOf("First", "Second"), `continue` = false))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, MessageNp2)
            es.send(0, InterfaceComponentText(MessageNp2, 1, "First"))
            es.send(0, InterfaceComponentText(MessageNp2, 2, "Second"))
        }
    }
}