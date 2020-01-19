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
import worlds.gregs.hestia.core.display.dialogue.model.type.MobChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp2
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadMob
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.MobDefinition
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

@ExtendWith(MockKExtension::class)
internal class MobChatSystemTest : MockkGame() {

    @SpyK
    private var system = MobChatSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    @RelaxedMockK
    lateinit var definitions: DefinitionReader<MobDefinition>

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system, MobDefinitionSystem(definitions))
    }

    @Test
    fun `Send mob chat`() {
        //Given
        every { definitions.get(any()) } returns MobDefinition().apply { name = "Man" }
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, MobChat(listOf("First", "Second"), 0, Expression.Laugh, large = false, `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, MobChat2)
            es.send(0, InterfaceHeadMob(MobChat2, 2, 1))
            es.send(0, InterfaceAnimation(MobChat2, 2, Expression.Laugh))
            es.send(0, InterfaceComponentText(MobChat2, 3, "Man"))
            es.send(0, InterfaceComponentText(MobChat2, 4, "First"))
            es.send(0, InterfaceComponentText(MobChat2, 5, "Second"))
        }
    }

    @Test
    fun `Send mob chat no continue`() {
        //Given
        every { definitions.get(any()) } returns MobDefinition().apply { name = "Man" }
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, MobChat(listOf("First", "Second"), 0, Expression.Laugh, large = false, `continue` = false))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, MobChatNp2)
            es.send(0, InterfaceHeadMob(MobChatNp2, 2, 1))
            es.send(0, InterfaceAnimation(MobChatNp2, 2, Expression.Laugh))
            es.send(0, InterfaceComponentText(MobChatNp2, 3, "Man"))
            es.send(0, InterfaceComponentText(MobChatNp2, 4, "First"))
            es.send(0, InterfaceComponentText(MobChatNp2, 5, "Second"))
        }
    }

    @Test
    fun `Send mob chat large`() {
        //Given
        every { definitions.get(any()) } returns MobDefinition().apply { name = "Man" }
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, MobChat(listOf("First", "Second"), 0, Expression.Laugh, large = true, `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, MobChat2)
            es.send(0, InterfaceHeadMob(MobChat2, 1, 1))
            es.send(0, InterfaceAnimation(MobChat2, 1, Expression.Laugh))
            es.send(0, InterfaceComponentText(MobChat2, 3, "Man"))
            es.send(0, InterfaceComponentText(MobChat2, 4, "First"))
            es.send(0, InterfaceComponentText(MobChat2, 5, "Second"))
        }
    }
}