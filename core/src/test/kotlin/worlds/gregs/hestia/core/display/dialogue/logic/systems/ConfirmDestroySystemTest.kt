package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
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
import worlds.gregs.hestia.core.display.dialogue.model.type.Destroy
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ConfirmDestroy
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

@ExtendWith(MockKExtension::class)
internal class ConfirmDestroySystemTest : MockkGame() {

    @SpyK
    private var system = ConfirmDestroySystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    @RelaxedMockK
    lateinit var definitions: DefinitionReader<ItemDefinition>

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system, ItemDefinitionSystem(definitions))
    }

    @Test
    fun `Send destroy`() {
        //Given
        every { definitions.get(any()) } returns ItemDefinition().apply { name = "Coins" }
        //When
        es.perform(0, Destroy("text", 995))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, ConfirmDestroy)
            es.send(0, InterfaceComponentText(ConfirmDestroy, 7, "text"))
            es.send(0, InterfaceComponentText(ConfirmDestroy, 8, "Coins"))
            es.send(0, InterfaceItem(ConfirmDestroy, 9, 995, 1))
        }
    }
}