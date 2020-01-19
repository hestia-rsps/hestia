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
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.display.dialogue.model.type.NpcChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp2
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadNpc
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.NpcDefinition
import worlds.gregs.hestia.service.cache.definition.systems.NpcDefinitionSystem

@ExtendWith(MockKExtension::class)
internal class NpcChatSystemTest : MockkGame() {

    @SpyK
    private var system = NpcChatSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    @RelaxedMockK
    lateinit var definitions: DefinitionReader<NpcDefinition>

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system, NpcDefinitionSystem(definitions))
    }

    @Test
    fun `Send npc chat`() {
        //Given
        every { definitions.get(any()) } returns NpcDefinition().apply { name = "Man" }
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, NpcChat(listOf("First", "Second"), 0, Expression.Laugh, large = false, `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, NpcChat2)
            es.send(0, InterfaceHeadNpc(NpcChat2, 2, 1))
            es.send(0, InterfaceAnimation(NpcChat2, 2, Expression.Laugh))
            es.send(0, InterfaceComponentText(NpcChat2, 3, "Man"))
            es.send(0, InterfaceComponentText(NpcChat2, 4, "First"))
            es.send(0, InterfaceComponentText(NpcChat2, 5, "Second"))
        }
    }

    @Test
    fun `Send npc chat no continue`() {
        //Given
        every { definitions.get(any()) } returns NpcDefinition().apply { name = "Man" }
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, NpcChat(listOf("First", "Second"), 0, Expression.Laugh, large = false, `continue` = false))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, NpcChatNp2)
            es.send(0, InterfaceHeadNpc(NpcChatNp2, 2, 1))
            es.send(0, InterfaceAnimation(NpcChatNp2, 2, Expression.Laugh))
            es.send(0, InterfaceComponentText(NpcChatNp2, 3, "Man"))
            es.send(0, InterfaceComponentText(NpcChatNp2, 4, "First"))
            es.send(0, InterfaceComponentText(NpcChatNp2, 5, "Second"))
        }
    }

    @Test
    fun `Send npc chat large`() {
        //Given
        every { definitions.get(any()) } returns NpcDefinition().apply { name = "Man" }
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, NpcChat(listOf("First", "Second"), 0, Expression.Laugh, large = true, `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, NpcChat2)
            es.send(0, InterfaceHeadNpc(NpcChat2, 1, 1))
            es.send(0, InterfaceAnimation(NpcChat2, 1, Expression.Laugh))
            es.send(0, InterfaceComponentText(NpcChat2, 3, "Man"))
            es.send(0, InterfaceComponentText(NpcChat2, 4, "First"))
            es.send(0, InterfaceComponentText(NpcChat2, 5, "Second"))
        }
    }
}