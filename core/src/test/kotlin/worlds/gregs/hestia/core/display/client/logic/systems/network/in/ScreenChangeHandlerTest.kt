package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketWriter
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.GraphicsOptions
import worlds.gregs.hestia.core.display.interfaces.model.components.GameFrame
import worlds.gregs.hestia.network.client.decoders.messages.ScreenChange
import worlds.gregs.hestia.tools.InterfaceTester

internal class ScreenChangeHandlerTest : InterfaceTester(WorldConfigurationBuilder().with(ScreenChangeHandler())) {

    private lateinit var entity: Entity
    private lateinit var gameFrame: GameFrame

    override fun start() {
        entity = world.createEntity()
        gameFrame = GameFrame()
        entity.edit().add(gameFrame)
    }

    @BeforeEach
    override fun setup() {
        super.setup()
        open(GraphicsOptions)
        gameFrame.displayMode = 0
        gameFrame.width = 0
        gameFrame.height = 0
    }

    @Test
    fun `No reload when display mode not changed`() {
        //When
        sendScreen()
        //Then
        assertReloaded(0)
    }

    @Test
    fun `No reload when graphics options interface closed`() {
        //Given
        close(GraphicsOptions)
        //When
        sendScreen(gameMode = 1)
        //Then
        assertReloaded(0)
    }

    @Test
    fun `Reload when graphics options interface open`() {
        //Given
        open(GraphicsOptions)
        //When
        sendScreen(gameMode = 1)
        //Then
        assertReloaded(1)
    }

    @Test
    fun `No reload when handling another entity`() {
        //When
        sendScreen(entityId = 1, gameMode = 1)
        //Then
        assertReloaded(0)
    }

    @Test
    fun `Reloads when game mode changes`() {
        //When
        sendScreen(gameMode = 1)
        //Then
        assertReloaded(1)
    }

    @Test
    fun `Screen fixed mode`() {
        //When
        sendScreen(gameMode = 1)
        //Then
        assertDisplayMode(1)
        assertReloaded(1)
    }

    @Test
    fun `Screen full mode`() {
        //Given
        gameFrame.displayMode = 1
        //When
        sendScreen(gameMode = 0)
        //Then
        assertDisplayMode(0)
        assertReloaded(1)
    }

    @Test
    fun `Screen resizable mode`() {
        //When
        sendScreen(gameMode = 2)
        //Then
        assertDisplayMode(2)
        assertReloaded(1)
    }

    @Test
    fun `Screen dimensions change when graphics interface open and no game mode change`() {
        //When
        sendScreen(width = 100, height = 100)
        //Then
        assertResize(100, 100)
    }

    @Test
    fun `Screen dimensions change when graphics interface closed and game mode changed`() {
        //Given
//        close(GraphicsOptionsSystem::class)
        //When
        sendScreen(width = 100, height = 100)
        //Then
        assertResize(100, 100)
    }

    private fun assertResize(width: Int, height: Int) {
        assertThat(gameFrame.width).isEqualTo(width)
        assertThat(gameFrame.height).isEqualTo(height)
    }

    private fun assertDisplayMode(mode: Int) {
        assertThat(gameFrame.displayMode).isEqualTo(mode)
    }

    private fun assertReloaded(times: Int) {
        verify(ui, times(times)).updateGameframe(0)
    }

    private fun sendScreen(entityId: Int = 0, gameMode: Int = 0, width: Int = 765, height: Int = 503) {
        val system = world.getSystem(ScreenChangeHandler::class)
        system.handle(entityId, ScreenChange(gameMode, width, height, 0))
    }

    private fun packet(gameMode: Int, width: Int, height: Int): Packet {
        val builder = PacketWriter()
        builder.writeByte(gameMode)
        builder.writeShort(width)
        builder.writeShort(height)
        return builder.build()
    }
}