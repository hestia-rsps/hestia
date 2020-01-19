package worlds.gregs.hestia.core.display.interfaces.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.display.interfaces.api.ContextMenus
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions
import worlds.gregs.hestia.core.display.request.model.components.ContextMenu

@ExtendWith(MockKExtension::class)
internal class ContextMenuSystemTest : MockkGame() {

    @SpyK
    private var system = ContextMenuSystem()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system)
    }

    @Test
    fun `Inserted context menu sets follow trade and assist`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, null, null, null, null, null, null, null)
        every { system.setOption(any(), any()) } answers { ContextMenus.ContextMenuResult.Success }
        //When
        world.createEntity().edit().add(menu)
        tick()
        //Then
        verify {
            system.setOption(any(), PlayerOptions.FOLLOW)
            system.setOption(any(), PlayerOptions.TRADE)
            system.setOption(any(), PlayerOptions.ASSIST)
        }
    }

    @Test
    fun `Set option`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, null, null, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.setOption(0, PlayerOptions.DUEL)
        //Then
        assertEquals(ContextMenus.ContextMenuResult.Success, result)
    }

    @Test
    fun `Set duplicate option`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, PlayerOptions.DUEL, null, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.setOption(0, PlayerOptions.DUEL)
        //Then
        assertEquals(ContextMenus.ContextMenuResult.Success, result)
    }

    @Test
    fun `Set option slot already in use`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, PlayerOptions.ATTACK, null, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.setOption(0, PlayerOptions.DUEL)
        //Then
        assertEquals(ContextMenus.ContextMenuResult.SlotInUse, result)
    }

    @Test
    fun `Set out of bounds slot option`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, null, null, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.setOption(0, PlayerOptions.ALLIANCE)
        //Then
        assertEquals(ContextMenus.ContextMenuResult.SlotInUse, result)
    }

    @Test
    fun `Remove option`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        val array = arrayOf(null, PlayerOptions.DUEL, null, null, null, null, null, null)
        every { menu.options } returns array
        world.createEntity().edit().add(menu)
        tick()
        //When
        system.removeOption(0, PlayerOptions.DUEL)
        //Then
        assertNull(array[1])
    }

    @Test
    fun `Has option`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, PlayerOptions.DUEL, null, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.hasOption(0, PlayerOptions.DUEL)
        //Then
        assertTrue(result)
    }

    @Test
    fun `Doesn't have option`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, PlayerOptions.CLAN_WAR, null, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.hasOption(0, PlayerOptions.DUEL)
        //Then
        assertFalse(result)
    }

    @Test
    fun `Doesn't have option wrong slot`() {
        //Given
        val menu = mockk<ContextMenu>(relaxed = true)
        every { menu.options } returns arrayOf(null, null, PlayerOptions.DUEL, null, null, null, null, null)
        world.createEntity().edit().add(menu)
        tick()
        //When
        val result = system.hasOption(0, PlayerOptions.DUEL)
        //Then
        assertFalse(result)
    }

}