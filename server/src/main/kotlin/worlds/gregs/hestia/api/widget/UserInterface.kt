package worlds.gregs.hestia.api.widget

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.widget.components.FullScreenWidget
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import kotlin.reflect.KClass

abstract class UserInterface : PassiveSystem() {

    /**
     * Handles all interface button presses
     * @param entityId The id of the entity who pressed the button
     * @param widgetId The id of the widget that the button is on
     * @param componentId The id of the component that was pressed
     * @param option The right click option that was pressed
     */
    abstract fun click(entityId: Int, widgetId: Int, componentId: Int, option: Int)

    /**
     * Opens a screen interface if one is not already open
     * @param entityId The entity to send the interface too
     * @param widget The [ScreenWidget] to open
     */
    abstract fun open(entityId: Int, widget: ScreenWidget)
    abstract fun open(entityId: Int, widget: FullScreenWidget)

    /**
     * Reloads all interfaces open
     * @param entityId The entity to reload
     */
    abstract fun reload(entityId: Int)

    /**
     * Checks if an entity has a [Widget] open
     * @param entityId The entities id
     * @param clazz The widget to check
     * @return If the entity [entityId] has interface [clazz] open
     */
    abstract fun contains(entityId: Int, clazz: KClass<out Widget>): Boolean

    /**
     * Closes open [ScreenWidget] interfaces
     * @param entityId The entity
     */
    abstract fun close(entityId: Int)

}