package worlds.gregs.hestia.core.plugins.widget.systems

import com.artemis.Entity
import com.artemis.utils.Bag
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.Chat
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.api.widget.Widget
import worlds.gregs.hestia.api.widget.components.Frame
import worlds.gregs.hestia.api.widget.components.FullScreenWidget
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.artemis.events.ButtonClick
import worlds.gregs.hestia.artemis.events.CloseDialogue
import worlds.gregs.hestia.artemis.events.send
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.DialogueBox
import worlds.gregs.hestia.core.plugins.widget.events.ScreenClosed
import worlds.gregs.hestia.core.plugins.widget.systems.frame.GameFrameSystem
import worlds.gregs.hestia.core.plugins.widget.systems.frame.chat.DialogueBoxSystem
import kotlin.reflect.KClass

class UserInterfaceSystem : UserInterface() {

    /**
     * List of all widgets
     */
    private lateinit var widgets: List<Widget>
    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        widgets = world.systems.filterIsInstance<Widget>()
    }

    @Subscribe
    private fun click(event: ButtonClick) {
        click(event.entityId, event.interfaceHash, event.widgetId, event.componentId, event.option)
    }

    /**
     * Closes dialogue
     */
    @Subscribe
    private fun closure(event: CloseDialogue) {
        val (entityId) = event
        if(contains(entityId, DialogueBoxSystem::class)) {
            close(entityId, DialogueBox::class)
        }
    }

    override fun click(entityId: Int, interfaceHash: Int, widgetId: Int, componentId: Int, option: Int) {
        widgets.filter { it.getId(entityId) == widgetId }.forEach {
            it.click(entityId, interfaceHash, componentId, option)
        }
    }

    override fun open(entityId: Int, widget: ScreenWidget) {
        open(world.getEntity(entityId), widget)
    }

    private fun open(entity: Entity, widget: ScreenWidget) {
        val has = widgets.filterIsInstance<BaseScreen>().any { it.subscription.activeEntityIds.get(entity.id) }
        if (has) {
            entity.send(Chat(0, 0, null, message = "Please close the interface you have open before opening another."))//TODO .message()
            return
        }

        entity.edit().add(widget)
    }

    override fun open(entityId: Int, widget: FullScreenWidget) {
        open(world.getEntity(entityId), widget)
    }

    private fun open(entity: Entity, widget: FullScreenWidget) {
        val has = widgets.filterIsInstance<BaseFullScreen>().any { it !is GameFrameSystem && it.subscription.activeEntityIds.get(entity.id) }
        if (has) {
            entity.send(Chat(0, 0, null, message = "Please close the interface you have open before opening another."))
            return
        }

        entity.edit().add(widget)
    }

    override fun reload(entityId: Int) {
        widgets.filter { it.subscription.activeEntityIds.get(entityId) }.forEach {
            it.open(entityId)
        }
    }

    override fun contains(entityId: Int, clazz: KClass<out Widget>): Boolean {
        return widgets.any { clazz.isInstance(it) && it.subscription.activeEntityIds.get(entityId) }//Couldn't we just do ContentMap<>.has(entityId)
    }

    override fun close(entityId: Int, clazz: KClass<out Frame>) {
        val edit = world.getEntity(entityId).edit()
        val all = world.componentManager.getComponentsFor(entityId, Bag())
        all.filter { clazz.isInstance(it) }.forEach {
            edit.remove(it)
            if(it is ScreenWidget) {
                es.dispatch(ScreenClosed(entityId, it))
            }
        }
    }

    override fun close(entityId: Int) {
        val edit = world.getEntity(entityId).edit()
        val all = world.componentManager.getComponentsFor(entityId, Bag())
        all.filterIsInstance<ScreenWidget>().forEach {
            edit.remove(it)
            es.dispatch(ScreenClosed(entityId, it))
        }
        all.filterIsInstance<FullScreenWidget>().forEach {
            if(it !is GameFrame) {//TODO handle better
                edit.remove(it)
            }
        }
    }
}