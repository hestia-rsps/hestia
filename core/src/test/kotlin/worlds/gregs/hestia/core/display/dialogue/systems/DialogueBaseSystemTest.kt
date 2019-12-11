package worlds.gregs.hestia.core.display.dialogue.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyOrder
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.display.dialogue.components.Dialogue
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.display.widget.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.game.MockkGameTest
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.services.send

@ExtendWith(MockKExtension::class)
internal class DialogueBaseSystemTest : MockkGameTest() {

    @SpyK
    var system = object : DialogueBaseSystem() {}

    @RelaxedMockK
    private lateinit var scope: TaskScope

    @RelaxedMockK
    private lateinit var queue: Tasks

    @SpyK
    var boxSystem = DialogueBoxSystem()

    @SpyK
    var component = TaskQueue()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, queue, boxSystem)
    }

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component)
        tick()
    }

    @Test
    fun `Get deferral doesn't return non-dialogues`() {
        //Given
        val deferral: DeferralType = mockk()
        val entityId = 0
        component.queue.add(scope)
        every { scope.deferral } returns deferral
        //When
        val result = system.getDeferral(entityId)
        //Then
        assertNull(result)
    }

    @Test
    fun `Get deferral returns dialogues`() {
        //Given
        val deferral: Dialogue = mockk()
        val entityId = 0
        component.queue.add(scope)
        every { scope.deferral } returns deferral
        //When
        val result = system.getDeferral(entityId)
        //Then
        assertNotNull(result)
    }

    @Test
    fun `Send opens chat and dispatches packets`() {
        //Given
        val entityId = 0
        val interfaceId = 250
        val componentStart = 3
        val title = "Title"
        val lines = listOf("Line one", "Line two")
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        every { boxSystem.openChatInterface(entityId, interfaceId) } answers {}
        //When
        system.send(entityId, interfaceId, componentStart, title, lines)
        //Then
        verifyOrder {
            boxSystem.openChatInterface(entityId, interfaceId)
            es.send(entityId, WidgetComponentText(interfaceId, componentStart, title))
            es.send(entityId, WidgetComponentText(interfaceId, componentStart + 1, "Line one"))
            es.send(entityId, WidgetComponentText(interfaceId, componentStart + 2, "Line two"))
        }
    }
}