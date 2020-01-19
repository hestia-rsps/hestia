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
import worlds.gregs.hestia.core.display.dialogue.model.type.Options
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar2
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceVisibility

@ExtendWith(MockKExtension::class)
internal class OptionsSystemTest : MockkGame() {

    @SpyK
    private var system = OptionsSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system)
    }

    @Test
    fun `Send options`() {
        //When
        es.perform(0, Options(listOf("First", "Second"), "title"))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Multi2)

            es.send(0, InterfaceVisibility(Multi2, 6, false))
            es.send(0, InterfaceVisibility(Multi2, 9, true))
            es.send(0, InterfaceComponentText(Multi2, 1, "title"))

            es.send(0, InterfaceComponentText(Multi2, 2, "First"))
            es.send(0, InterfaceComponentText(Multi2, 3, "Second"))
        }
    }

    @Test
    fun `Send title-less options`() {
        //When
        es.perform(0, Options(listOf("First", "Second"), null))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Multi2)

            es.send(0, InterfaceComponentText(Multi2, 2, "First"))
            es.send(0, InterfaceComponentText(Multi2, 3, "Second"))
        }
    }

    @Test
    fun `Send options large title`() {
        //When
        es.perform(0, Options(listOf("First", "Second"), "a title which exceeds thirty characters"))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Multi2)

            es.send(0, InterfaceVisibility(Multi2, 6, true))
            es.send(0, InterfaceVisibility(Multi2, 9, false))
            es.send(0, InterfaceComponentText(Multi2, 1, "a title which exceeds thirty characters"))

            es.send(0, InterfaceComponentText(Multi2, 2, "First"))
            es.send(0, InterfaceComponentText(Multi2, 3, "Second"))
        }
    }

    @Test
    fun `Send options multiline title`() {
        //When
        es.perform(0, Options(listOf("First", "Second"), "multiline<br>title"))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, MultiVar2)

            es.send(0, InterfaceVisibility(MultiVar2, 5, false))
            es.send(0, InterfaceVisibility(MultiVar2, 6, true))
            es.send(0, InterfaceComponentText(MultiVar2, 0, "multiline<br>title"))

            es.send(0, InterfaceComponentText(MultiVar2, 1, "First"))
            es.send(0, InterfaceComponentText(MultiVar2, 2, "Second"))
        }
    }

    @Test
    fun `Send multi line options`() {
        //When
        es.perform(0, Options(listOf("First", "Second<br>line"), "title"))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Multi2Chat)

            es.send(0, InterfaceVisibility(Multi2Chat, 6, false))
            es.send(0, InterfaceVisibility(Multi2Chat, 9, true))
            es.send(0, InterfaceComponentText(Multi2Chat, 1, "title"))

            es.send(0, InterfaceComponentText(Multi2Chat, 2, "First"))
            es.send(0, InterfaceComponentText(Multi2Chat, 3, "Second<br>line"))
        }
    }

    @Test
    fun `Send options multiline title and options`() {
        //When
        es.perform(0, Options(listOf("First", "Second<br>line"), "multiline<br>title"))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, MultiVar2)

            es.send(0, InterfaceVisibility(MultiVar2, 5, false))
            es.send(0, InterfaceVisibility(MultiVar2, 6, true))
            es.send(0, InterfaceComponentText(MultiVar2, 0, "multiline<br>title"))

            es.send(0, InterfaceComponentText(MultiVar2, 1, "First"))
            es.send(0, InterfaceComponentText(MultiVar2, 2, "Second<br>line"))
        }
    }

}