package worlds.gregs.hestia.core.display.window.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.window.api.ContextMenus
import worlds.gregs.hestia.core.display.window.model.PlayerOptions
import worlds.gregs.hestia.core.display.window.model.components.ContextMenu
import worlds.gregs.hestia.network.client.encoders.messages.PlayerContextMenuOption

class ContextMenuSystem : ContextMenus() {

    private lateinit var contextMenuMapper: ComponentMapper<ContextMenu>
    private lateinit var es: EventSystem

    override fun inserted(entityId: Int) {
        setOption(entityId, PlayerOptions.FOLLOW)
        setOption(entityId, PlayerOptions.TRADE)
        setOption(entityId, PlayerOptions.ASSIST)
    }

    override fun setOption(entityId: Int, option: PlayerOptions): ContextMenuResult {
        val contextMenu = contextMenuMapper.get(entityId)
        val current = contextMenu.options[option.slot]
        return if(current != null && current != option) {
            ContextMenuResult.SlotInUse
        } else {
            contextMenu.options[option.slot] = option
            es.send(entityId, PlayerContextMenuOption(option.string, option.slot, option.top))
            ContextMenuResult.Success
        }
    }

    override fun removeOption(entityId: Int, option: PlayerOptions) {
        val contextMenu = contextMenuMapper.get(entityId)
        contextMenu.options[option.slot] = null
    }

    override fun hasOption(entityId: Int, option: PlayerOptions): Boolean {
        val contextMenu = contextMenuMapper.get(entityId)
        return contextMenu.options[option.slot] != null
    }
}