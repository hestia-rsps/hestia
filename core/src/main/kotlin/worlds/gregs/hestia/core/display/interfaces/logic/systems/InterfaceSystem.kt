package worlds.gregs.hestia.core.display.interfaces.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.interfaces.model.components.GameFrame
import worlds.gregs.hestia.core.display.interfaces.model.components.InterfaceRelationships
import worlds.gregs.hestia.core.display.interfaces.model.events.*
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseWindow
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.request.RefreshInterface
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceCloseMessage
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceOpenMessage
import worlds.gregs.hestia.network.client.encoders.messages.WindowUpdate
import worlds.gregs.hestia.service.cache.definition.systems.InterfaceDefinitionSystem

class InterfaceSystem : Interfaces() {

    private lateinit var interfaceRelationshipsMapper: ComponentMapper<InterfaceRelationships>
    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private lateinit var definitions: InterfaceDefinitionSystem
    private lateinit var es: EventSystem

    private val windows = mutableMapOf<Int, Window>()

    override fun initialize() {
        super.initialize()
        Window.values().forEach { window ->
            window.ids.forEach { id ->
                setWindow(id, window)
            }
        }
    }

    override fun inserted(entityId: Int) {
        val gameFrame = gameFrameMapper.get(entityId)
        //Gameframe
        openInterface(entityId, if (gameFrame.resizable) ResizableGameframe else FixedGameframe)
        //Gameframe components
        components.forEach {
            openInterface(entityId, it)
        }
    }

    override fun setWindow(id: Int, window: Window) {
        windows[id] = window
    }

    override fun openInterface(entityId: Int, id: Int, force: Boolean): InterfaceResult {
        val relationships = interfaceRelationshipsMapper.get(entityId).relationships
        val gameFrame = gameFrameMapper.get(entityId)

        val window = getWindow(id)
        val parent = getParent(window, gameFrame)
        val index = window.getIndex(gameFrame.resizable)
        if (relationships.containsKey(parent)) {
            if (relationships[parent] == null) {
                relationships[parent] = mutableListOf()
            }

            if (relationships[parent]!!.contains(id)) {//Interface already open
                return InterfaceResult.Issue.AlreadyOpen
            }

            //If the slot is already in use
            if (!force && relationships[parent]!!.any { getIndex(it, gameFrame.resizable) == index }) {
                return InterfaceResult.Issue.AnotherOpen
            }

            relationships[id] = null
            relationships[parent]!!.add(id)//Add child
            send(entityId, parent, index, id)
            return InterfaceResult.Opened
        }
        return InterfaceResult.Issue.MissingParent(parent)
    }

    private fun send(entityId: Int, parent: Int, index: Int, id: Int) {
        val window = getWindow(id)
        if (window == Window.FULL_SCREEN) {
            es.send(entityId, WindowUpdate(id, 0))
        } else {
            es.send(entityId, InterfaceOpenMessage(isPermanent(window), parent, index, id))
        }
        es.perform(entityId, InterfaceOpened(id))
    }

    override fun closeInterface(entityId: Int, id: Int) {
        val interfaceRelationships = interfaceRelationshipsMapper.get(entityId)
        val relationships = interfaceRelationships.relationships
        val parent = relationships.getParent(id)
        if (parent != id) {
            val gameFrame = gameFrameMapper.get(entityId)
            val index = getIndex(id, gameFrame.resizable)
            relationships[parent]!!.remove(id)
            es.send(entityId, InterfaceCloseMessage(parent, index))
            es.perform(entityId, InterfaceClosed(id))
        }
        //Otherwise interface not open
    }

    override fun closeWindow(entityId: Int, window: Window) {
        val id = getInterface(entityId, window)
        if (id != null) {
            closeInterface(entityId, id)
        }
    }

    override fun updateGameframe(entityId: Int) {
        val relationships = interfaceRelationshipsMapper.get(entityId).relationships
        val gameFrame = gameFrameMapper.get(entityId)
        val previous = if (gameFrame.resizable) FixedGameframe else ResizableGameframe
        val current = if (gameFrame.resizable) ResizableGameframe else FixedGameframe
        val relationship = relationships[previous]
        if (relationship != null) {//If has children
            relationships[current] = relationship
            relationships.remove(previous)//Update id
        }
        //Re-open all windows
        relationships.walk(current) { child, parent ->
            send(entityId, parent, getIndex(child, gameFrame.resizable), child)
            false
        }
    }

    override fun getInterface(entityId: Int, window: Window): Int? {
        val relationships = interfaceRelationshipsMapper.get(entityId).relationships
        val gameFrame = gameFrameMapper.get(entityId)

        val paneParent = getParent(window, gameFrame)
        val index = if (gameFrame.resizable) window.resizable else window.fixed
        return relationships.walk(0) { id, parent ->
            parent == paneParent && getIndex(id, gameFrame.resizable) == index
        }
    }

    override fun hasInterface(entityId: Int, window: Window): Boolean {
        return hasInterface(entityId, getInterface(entityId, window) ?: return false)
    }

    override fun hasInterface(entityId: Int, id: Int): Boolean {
        val relationships = interfaceRelationshipsMapper.get(entityId).relationships
        return relationships.containsKey(id)
    }

    override fun verify(entityId: Int, hash: Int): Boolean {
        val relationships = interfaceRelationshipsMapper.get(entityId)?.relationships ?: return false
        //Check entity has interface open
        val id = hash shr 16
        if (!relationships.containsKey(id)) {
            return false//Interface not open
        }

        //Check component is real
        val componentId = hash - (id shl 16)
        val definition = definitions.get(id)
        if (!definition.containsKey(componentId)) {
            return false//Invalid component index
        }

        return true
    }

    override fun refreshInterface(entityId: Int, id: Int) {
        val relationships = interfaceRelationshipsMapper.get(entityId).relationships
        relationships.walk(id) { interfaceId, _ ->
            es.perform(entityId, InterfaceRefresh(interfaceId))
            false
        }
    }

    override fun getWindow(id: Int): Window = windows[id] ?: Window.MAIN_SCREEN

    @Subscribe
    private fun openInterface(event: OpenInterface) {
        val result = openInterface(event.entity, event.id, event.force)
        if (result is InterfaceResult.Issue) {
            logger.warn("Issue opening interface $event $result")
        }
    }

    @Subscribe
    private fun closeInterface(event: CloseInterface) = closeInterface(event.entity, event.id)

    @Subscribe
    private fun closeWindow(event: CloseWindow) {
        closeInterface(event.entity, getInterface(event.entity, event.window) ?: return)
    }

    @Subscribe
    private fun refreshInterface(event: RefreshInterface) = refreshInterface(event.entity, event.id)

    @Subscribe
    private fun click(event: ButtonClick) {
        val (entityId, hash, from, to, option) = event
        if (verify(entityId, hash)) {
            val id = hash shr 16
            val component = hash - (id shl 16)
            es.perform(entityId, InterfaceInteraction(id, component, from, to, option))
        }
    }

    private fun getIndex(id: Int, resizeable: Boolean): Int {
        return getWindow(id).getIndex(resizeable)
    }

    companion object {
        private fun Window.getIndex(resizeable: Boolean): Int {
            return if (resizeable) resizable else fixed
        }

        private fun isPermanent(window: Window): Boolean {
            return window != Window.MAIN_SCREEN
        }

        /**
         * Walks recursively through [root] and all of it's children calling [predicate] each time
         * @param root The interface to walk down
         * @param parent The [root]'s parent id
         * @param predicate If true then walk returns the found id
         * @return The id of the first interface when [predicate] returns true
         */
        private fun Map<Int, List<Int>?>.walk(root: Int, parent: Int = getParent(root), predicate: (id: Int, parent: Int) -> Boolean): Int? {
            if (predicate.invoke(root, parent)) {
                return root
            }

            if (containsKey(root)) {
                for (child in getValue(root) ?: return null) {
                    val result = walk(child, root, predicate)
                    if (result != null) {
                        return result
                    }
                }
            }
            return null
        }

        /**
         * Finds the parent of a interface
         * @return The parent id OR the child's id
         */
        private fun Map<Int, List<Int>?>.getParent(child: Int): Int {
            for ((parentId, children) in this) {
                if (children != null && children.contains(child)) {
                    return parentId
                }
            }
            return child
        }

        /**
         * Returns the true parent interface id for a [Window]
         * @param window The window who's parent to get
         * @param gameFrame The gameframe information
         * @return The parent id of the window
         */
        private fun getParent(window: Window, gameFrame: GameFrame): Int =
                if (window.parent != -1) window.parent else if (gameFrame.resizable) ResizableGameframe else FixedGameframe

        private val logger = LoggerFactory.getLogger(InterfaceSystem::class.java)!!
        private val components = listOf(
                ChatBox, ChatBackground, FilterButtons, PrivateChat,//Chat box
                HealthOrb, PrayerOrb, EnergyOrb, SummoningOrb,//Minimap
                CombatStyles, TaskSystem, Stats, QuestJournals, Inventory, WornEquipment, PrayerList, ModernSpellbook,//Tabs
                FriendsList, FriendsChat, ClanChat, Options, Emotes, MusicPlayer, Notes,
                AreaStatusIcon
        )
    }
}