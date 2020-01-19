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
import worlds.gregs.hestia.core.display.dialogue.model.type.DoubleChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat2
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadPlayer

@ExtendWith(MockKExtension::class)
internal class DoubleChatSystemTest : MockkGame() {

    @SpyK
    private var system = DoubleChatSystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(interfaces, system)
    }

    @Test
    fun `Send double chat`() {
        //Given
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, DoubleChat(listOf("First", "Second"), 0, Expression.Sad, Expression.Afraid))
        //Then
        verifyOrder {
            interfaces.closeWindow(0, Window.DIALOGUE_BOX)
            interfaces.openInterface(0, DoubleChat2)
            es.send(0, InterfaceHeadMob(DoubleChat2, 1, 1))
            es.send(0, InterfaceAnimation(DoubleChat2, 1, Expression.Sad))

            es.send(0, InterfaceHeadPlayer(DoubleChat2, 2))
            es.send(0, InterfaceAnimation(DoubleChat2, 2, Expression.Afraid))

            es.send(0, InterfaceComponentText(DoubleChat2, 3, "First"))
            es.send(0, InterfaceComponentText(DoubleChat2, 4, "Second"))
        }
    }
}