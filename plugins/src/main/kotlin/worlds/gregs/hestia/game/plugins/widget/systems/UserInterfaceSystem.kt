package worlds.gregs.hestia.game.plugins.widget.systems

import com.artemis.Entity
import com.artemis.utils.Bag
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.api.widget.Widget
import worlds.gregs.hestia.api.widget.components.Frame
import worlds.gregs.hestia.api.widget.components.FullScreenWidget
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.game.events.ButtonClick
import worlds.gregs.hestia.game.events.OpenWidget
import kotlin.reflect.KClass

class UserInterfaceSystem : UserInterface() {

    /**
     * List of all widgets
     */
    private lateinit var widgets: List<Widget>

    override fun initialize() {
        super.initialize()
        widgets = world.systems.filterIsInstance<Widget>()
    }

    @Subscribe
    private fun click(event: ButtonClick) {
        click(event.entityId, event.widgetId, event.componentId, event.option)
    }

    override fun click(entityId: Int, widgetId: Int, componentId: Int, option: Int) {
        widgets.filter { it.getId(entityId) == widgetId }.forEach {
            it.click(entityId, componentId, option)
        }
    }

    override fun open(entityId: Int, widget: ScreenWidget) {
        open(world.getEntity(entityId), widget)
    }

    private fun open(entity: Entity, widget: ScreenWidget) {
        val has = widgets.filterIsInstance<BaseScreen>().any { it.subscription.entities.size() > 0 }
        if (has) {
            println("Screen widget already open")
//            entity.message("Please close the interface you have open before opening another.")
            return
        }

        entity.edit().add(widget)
    }

    override fun open(entityId: Int, widget: FullScreenWidget) {
        open(world.getEntity(entityId), widget)
    }

    private fun open(entity: Entity, widget: FullScreenWidget) {
        val has = widgets.filterIsInstance<BaseFullScreen>().any { it.subscription.entities.contains(entity.id) }
        if (has) {
            println("Full screen widget already open")
//            entity.message("Please close the interface you have open before opening another.")
            return
        }

        entity.edit().add(widget)
    }

    override fun reload(entityId: Int) {
        widgets.filter { it.subscription.entities.contains(entityId) }.forEach {
            it.open(entityId)
        }
    }

    override fun contains(entityId: Int, clazz: KClass<out Widget>): Boolean {
        return widgets.any { clazz.isInstance(it) && it.subscription.entities.contains(entityId) }
    }

    override fun close(entityId: Int, clazz: KClass<out Frame>) {
        val edit = world.getEntity(entityId).edit()
        val all = world.componentManager.getComponentsFor(entityId, Bag())
        all.filter { clazz.isInstance(it) }.forEach {
            edit.remove(it)
        }
    }

    override fun close(entityId: Int) {
        val edit = world.getEntity(entityId).edit()
        val all = world.componentManager.getComponentsFor(entityId, Bag())
        all.filterIsInstance<ScreenWidget>().forEach {
            edit.remove(it)
        }
        all.filterIsInstance<FullScreenWidget>().forEach {
            edit.remove(it)
        }
    }

    @Subscribe
    fun open(event: OpenWidget) {
//        es.send(event.entityId, InterfaceOpen(event.widgetId))
    }
}