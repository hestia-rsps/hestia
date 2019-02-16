package worlds.gregs.hestia.game.archetypes

import com.artemis.Archetype
import com.artemis.World
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.services.Loader
import world.gregs.hestia.core.services.plural
import kotlin.reflect.KClass
import kotlin.system.measureNanoTime

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
        this.world = world
    }

    fun load(loader: Loader) {
        var count = 0
        val time = measureNanoTime {
            val factories = loader.load<ArchetypeFactory>("worlds.gregs.hestia.game.archetypes")
            factories.forEach { factory ->
                add(factory)
            }
            count = factories.size
        }
        logger.debug("$count ${"archetype".plural(count)} loaded in ${time / 1000000}ms")
    }

}