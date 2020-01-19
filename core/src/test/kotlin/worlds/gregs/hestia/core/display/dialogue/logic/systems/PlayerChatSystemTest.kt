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
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.display.dialogue.model.type.PlayerChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp2
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadPlayer

@ExtendWith(MockKExtension::class)
internal class PlayerChatSystemTest : MockkGame() {

    @SpyK
    private var system = PlayerChatSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system)
    }

    @Test
    fun `Send player chat`() {
        //Given
        world.createEntity().edit().add(DisplayName().apply { name = "name" })
        //When
        es.perform(0, PlayerChat(listOf("First", "Second"), Expression.Laugh, large = false, `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Chat2)
            es.send(0, InterfaceHeadPlayer(Chat2, 2))
            es.send(0, InterfaceAnimation(Chat2, 2, Expression.Laugh))
            es.send(0, InterfaceComponentText(Chat2, 3, "name"))
            es.send(0, InterfaceComponentText(Chat2, 4, "First"))
            es.send(0, InterfaceComponentText(Chat2, 5, "Second"))
        }
    }

    @Test
    fun `Send player chat no continue`() {
        //Given
        world.createEntity().edit().add(DisplayName().apply { name = "name" })
        //When
        es.perform(0, PlayerChat(listOf("First", "Second"), Expression.Laugh, large = false, `continue` = false))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, ChatNp2)
            es.send(0, InterfaceHeadPlayer(ChatNp2, 2))
            es.send(0, InterfaceAnimation(ChatNp2, 2, Expression.Laugh))
            es.send(0, InterfaceComponentText(ChatNp2, 3, "name"))
            es.send(0, InterfaceComponentText(ChatNp2, 4, "First"))
            es.send(0, InterfaceComponentText(ChatNp2, 5, "Second"))
        }
    }

    @Test
    fun `Send player chat large`() {
        //Given
        world.createEntity().edit().add(DisplayName().apply { name = "name" })
        //When
        es.perform(0, PlayerChat(listOf("First", "Second"), Expression.Laugh, large = true, `continue` = true))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, Chat2)
            es.send(0, InterfaceHeadPlayer(Chat2, 1))
            es.send(0, InterfaceAnimation(Chat2, 1, Expression.Laugh))
            es.send(0, InterfaceComponentText(Chat2, 3, "name"))
            es.send(0, InterfaceComponentText(Chat2, 4, "First"))
            es.send(0, InterfaceComponentText(Chat2, 5, "Second"))
        }
    }
}