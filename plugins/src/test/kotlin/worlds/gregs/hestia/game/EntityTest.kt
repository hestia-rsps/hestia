package worlds.gregs.hestia.game

import com.artemis.*
import com.artemis.utils.IntBag
import worlds.gregs.hestia.services.Aspect
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.services.add

internal class EntityTest {

    lateinit var world: World
    private lateinit var test: Archetype

    @BeforeEach
    fun setUp() {
        val config = WorldConfigurationBuilder().build()
        world = World(config)
        test = ArchetypeBuilder().add(Position::class).build(world)
    }

    @Test
    fun test() {
        world.create(test)

    }

    @Test
    fun insertion() {
        val subscription = world.aspectSubscriptionManager.get(Aspect.all(Position::class))

        //Ignored
        world.create(test)//0
        world.process()

        //Insert single
        val listener = object: EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag?) {
                assertThat(entities?.contains(1)).isTrue()
            }

            override fun removed(entities: IntBag?) {
            }

        }
        subscription.addSubscriptionListener(listener)
        world.create(test)//1
        world.process()

        subscription.removeSubscriptionListener(listener)

        //Insert multiple
        subscription.addSubscriptionListener(object: EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag?) {
                assertThat(entities?.size()).isEqualTo(2)
            }

            override fun removed(entities: IntBag?) {
            }

        })

        world.create(test)//2
        world.create(test)//3
        world.process()

    }

    @Test
    fun removal() {
        val subscription = world.aspectSubscriptionManager.get(Aspect.all(Position::class))

        //Ignored
        world.create(test)//0
        world.create(test)//1
        world.create(test)//2
        world.create(test)//3
        world.process()

        //Remove single
        var listener: EntitySubscription.SubscriptionListener = object: EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag?) {
            }

            override fun removed(entities: IntBag?) {
                assertThat(entities?.contains(0)).isTrue()
            }

        }
        subscription.addSubscriptionListener(listener)
        world.delete(0)
        world.process()

        subscription.removeSubscriptionListener(listener)

        //Remove multiple
        listener = object: EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag?) {
                assertThat(entities?.size()).isEqualTo(2)
            }

            override fun removed(entities: IntBag?) {
            }
        }
        subscription.addSubscriptionListener(listener)
        world.delete(2)
        world.delete(3)
        world.process()

        subscription.removeSubscriptionListener(listener)

        //One remaining
        assertThat(world.entityManager.isActive(1)).isTrue()
    }

}