package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue

@ExtendWith(MockKExtension::class)
internal class DialogueBaseSystemTest : MockkGame() {

    @SpyK
    var system = object : DialogueBaseSystem() {
        override val logger = LoggerFactory.getLogger(DialogueBaseSystemTest::class.java)!!
    }

    @RelaxedMockK
    private lateinit var tasks: Tasks
    @RelaxedMockK
    private lateinit var interfaces: Interfaces

    @SpyK
    var boxSystem = DialogueBoxSystem()

    @SpyK
    var component = TaskQueue()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, boxSystem, interfaces)
    }

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component)
        tick()
    }

    @Test
    fun `Send opens chat and dispatches packets`() {
        //Given
        val entityId = 0
        val id = 250
        val componentStart = 3
        val title = "Title"
        val lines = listOf("Line one", "Line two")
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        every { boxSystem.openChat(entityId, id) } answers {}
        //When
        system.send(entityId, id, componentStart, title, lines)
        //Then
        verifyOrder {
            boxSystem.openChat(entityId, id)
            es.send(entityId, WidgetComponentText(id, componentStart, title))
            es.send(entityId, WidgetComponentText(id, componentStart + 1, "Line one"))
            es.send(entityId, WidgetComponentText(id, componentStart + 2, "Line two"))
        }
    }
}