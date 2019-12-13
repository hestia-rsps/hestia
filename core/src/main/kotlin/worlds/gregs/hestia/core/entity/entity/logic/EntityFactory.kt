package worlds.gregs.hestia.core.entity.entity.logic

import com.artemis.Archetype
import com.artemis.World
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import kotlin.reflect.KClass

object EntityFactory {
    private lateinit var world: World
    private val archetypes = HashMap<KClass<out ArchetypeFactory>, Archetype>()
    private val logger = LoggerFactory.getLogger(EntityFactory::class.java)

    fun <F : ArchetypeFactory> add(factory: F) {
        archetypes[factory::class] = factory.getBuilder().build(world)
    }

    fun <F : ArchetypeFactory> create(factory: KClass<F>): Int {
        return world.create(archetypes[factory])
    }

    fun init(world: World) {
        EntityFactory.world = world
        logger.debug("${archetypes.size} ${"archetype".plural(archetypes.size)} loaded.")
    }

}