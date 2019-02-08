package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.game.plugins.widget.systems.screen.GraphicsOptionsSystem
import worlds.gregs.hestia.services.getSystem
import worlds.gregs.hestia.tools.InterfaceTester

internal class ScreenHandlerTest : InterfaceTester(WorldConfigurationBuilder().with(ScreenHandler())) {

    private lateinit var entity: Entity
    private lateinit var gameFrame: GameFrame

    override fun start() {
        entity = world.createEntity()
        gameFrame = GameFrame()
        entity.edit().add(gameFrame)
    }

    @BeforeEach
    fun setup() {
        open(GraphicsOptionsSystem::class)
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
        close(GraphicsOptionsSystem::class)
        //When
        sendScreen(gameMode = 1)
        //Then
        assertReloaded(0)
    }

    @Test
    fun `Reload when graphics options interface open`() {
        //Given
        open(GraphicsOptionsSystem::class)
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
        close(GraphicsOptionsSystem::class)
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
        assertReloaded(times, 0)
    }

    private fun sendScreen(entityId: Int = 0, gameMode: Int = 0, width: Int = 765, height: Int = 503) {
        val system = world.getSystem(ScreenHandler::class)
        val packet = packet(gameMode, width, height)
        system.handle(entityId, packet)
    }

    private fun packet(gameMode: Int, width: Int, height: Int): Packet {
        val builder = Packet.Builder()
        builder.writeByte(gameMode)
        builder.writeShort(width)
        builder.writeShort(height)
        return builder.build()
    }
}