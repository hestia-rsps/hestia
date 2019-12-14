package worlds.gregs.hestia.core.entity.bot.logic.systems

import com.artemis.Archetype
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.bot.logic.BotFactory
import worlds.gregs.hestia.core.entity.bot.logic.BotSpawns
import worlds.gregs.hestia.core.entity.bot.model.events.CreateBot
import worlds.gregs.hestia.core.entity.entity.model.components.Position

class BotCreation(private val spawn: Boolean) : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = BotFactory().getBuilder().build(world)
        if(spawn) {
            BotSpawns.values().forEach {
                create(it.displayName, it.x, it.y, it.plane)
            }
        }
    }

    @Subscribe
    fun create(event: CreateBot): Int {
        return create(event.name, event.x, event.y, event.plane)
    }

    private fun create(name: String, x: Int, y: Int, plane: Int): Int {
        val entityId = world.create(archetype)

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = name

        val position = positionMapper.get(entityId)
        position.set(x, y, plane)
        return entityId
    }
}