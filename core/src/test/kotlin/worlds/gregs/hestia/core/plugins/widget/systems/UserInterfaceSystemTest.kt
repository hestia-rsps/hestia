package worlds.gregs.hestia.core.plugins.widget.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.artemis.utils.Bag
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.api.widget.Widget
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.game.GameTest

internal class UserInterfaceSystemTest : GameTest(WorldConfigurationBuilder()) {

    private val widget = mock<Widget>()
    private lateinit var entity: Entity
    private val ui = UserInterfaceSystem()
    private val system = mock<BaseScreen>()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(widget, ui, system)
    }

    override fun start() {
        entity = world.createEntity()
        world.createEntity()
    }

    @BeforeEach
    override fun setup() {
        super.setup()
        reset(widget)
        whenever(widget.getId(any())).thenReturn(11)
        whenever(system.subscription).thenReturn(mock())
        whenever(system.subscription.entities).thenReturn(mock())
        whenever(widget.subscription).thenReturn(mock())
        whenever(widget.subscription.entities).thenReturn(mock())
    }

    @Test
    fun `Clicks multiple times`() {
        //When
        click()
        click()
        //Then
        assertClick(2)
    }

    @Test
    fun `Clicks entity correctly`() {
        //When
        click(entity = 1)
        //Then
        assertClick(1, entity = 1)
    }

    @Test
    fun `Clicks entity incorrectly`() {
        //When
        click(entity = 1)
        //Then
        assertClick(0, entity = 0)
    }

    @Test
    fun `Clicks widget correctly`() {
        //When
        click(widget = 11)
        //Then
        assertClick(1)
    }

    @Test
    fun `Clicks widget incorrectly`() {
        //When
        click(entity = 12)
        //Then
        assertClick(0)
    }

    @Test
    fun `Clicks widget component correctly`() {
        //When
        click(component = 10)
        //Then
        assertClick(1, component = 10)
    }

    @Test
    fun `Clicks widget component incorrectly`() {
        //When
        click(component = 4)
        //Then
        assertClick(0, component = 10)
    }

    @Test
    fun `Clicks widget option correctly`() {
        //When
        click(option = 1)
        //Then
        assertClick(1, option = 1)
    }

    @Test
    fun `Clicks widget option incorrectly`() {
        //When
        click(option = 0)
        //Then
        assertClick(0, option = 1)
    }

    @Test
    fun `Open screen widget`() {
        //Given
        whenever(system.subscription.entities.size()).thenReturn(0)
        //When
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //Then
        assertThat(entity.getComponents(Bag())).contains(widget)
    }

    @Test
    fun `Open other entity screen widget`() {
        //Given
        whenever(system.subscription.entities.contains(entity.id)).thenReturn(true)
        //When
        val widget = mock<ScreenWidget>()
        open(entity = 1, widget = widget)
        tick()
        //Then
        assertThat(entity.getComponents(Bag())).doesNotContain(widget)
    }

    @Test
    fun `Fail to open second screen widget`() {
        //Given
        whenever(system.subscription.entities.contains(entity.id)).thenReturn(true)
        //When
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //Then
        assertThat(entity.getComponents(Bag())).doesNotContain(widget)
    }

    @Test
    fun `Close all screen widgets`() {
        //Given
        whenever(system.subscription.entities.size()).thenReturn(0)
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //When
        close()
        tick()
        //Then
        assertThat(entity.getComponents(Bag())).doesNotContain(widget)
    }

    @Test
    fun `Close other entity screen widgets`() {
        //Given
        whenever(system.subscription.entities.size()).thenReturn(0)
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //When
        close(1)
        tick()
        //Then
        assertThat(entity.getComponents(Bag())).contains(widget)
    }

    @Test
    fun `Reload widgets`() {
        //Given
        whenever(system.subscription.entities.contains(any())).thenReturn(false)
        whenever(widget.subscription.entities.contains(any())).thenReturn(true)
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //When
        reload()
        //Then
        verify(this.widget, times(1)).open(0)
    }

    @Test
    fun `Reload another entities widgets`() {
        //Given
        whenever(system.subscription.entities.contains(any())).thenReturn(false)
        whenever(widget.subscription.entities.contains(any())).thenReturn(true)
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //When
        reload(1)
        //Then
        verify(this.widget, times(0)).open(0)
    }

    @Test
    fun `Fail to reload widgets`() {
        //Given
        whenever(system.subscription.entities.contains(any())).thenReturn(false)
        whenever(widget.subscription.entities.contains(any())).thenReturn(false)
        val widget = mock<ScreenWidget>()
        open(widget = widget)
        tick()
        //When
        reload()
        //Then
        verify(this.widget, times(0)).open(0)
    }

    private fun reload(entity: Int = 0) {
        ui.reload(entity)
    }

    private fun close(entity: Int = 0) {
        ui.close(entity)
    }

    private fun open(entity: Int = 0, widget: ScreenWidget) {
        ui.open(entity, widget)
    }

    private fun click(entity: Int = 0, widget: Int = 11, component: Int = 0, option: Int = 0) {
        ui.click(entity, 0, widget, component, option)
    }

    private fun assertClick(times: Int, entity: Int = 0, component: Int = 0, option: Int = 0) {
        verify(widget, times(times)).click(entity, 0, component, option)
    }

}