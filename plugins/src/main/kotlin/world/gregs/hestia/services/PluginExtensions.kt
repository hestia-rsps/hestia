package world.gregs.hestia.services

import com.artemis.*
import com.artemis.Aspect
import org.apache.commons.text.WordUtils
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.entity.Player
import kotlin.reflect.KClass

/*
    Kotlin extension helpers
 */

/*
    Artemis
 */
fun <T : Component> World.getMapper(type: KClass<T>): ComponentMapper<T> {
    return getMapper(type.java)
}

class Aspect {
    companion object {
        fun all(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
            return com.artemis.Aspect.all(*clazz.map { it.java }.toTypedArray())
        }

        fun one(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
            return com.artemis.Aspect.one(*clazz.map { it.java }.toTypedArray())
        }

        fun exclude(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
            return com.artemis.Aspect.exclude(*clazz.map { it.java }.toTypedArray())
        }
    }
}

fun Aspect.Builder.all(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return all(*clazz.map { it.java }.toTypedArray())
}

fun Aspect.Builder.one(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return one(*clazz.map { it.java }.toTypedArray())
}

fun Aspect.Builder.exclude(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return exclude(*clazz.map { it.java }.toTypedArray())
}

fun ArchetypeBuilder.add(vararg clazz: KClass<out Component>): ArchetypeBuilder {
    return add(*clazz.map { it.java }.toTypedArray())
}

fun World.players(): IntArray {
    return aspectSubscriptionManager.get(world.gregs.hestia.services.Aspect.all(Player::class)).entities.toArray()
}

fun World.mobs(): IntArray {
    return aspectSubscriptionManager.get(world.gregs.hestia.services.Aspect.all(Mob::class)).entities.toArray()
}

fun <T : Component> Entity.getComponent(type: KClass<T>): T? {
    val tf = world.componentManager.typeFactory
    return getComponent(tf.getTypeFor(type.java)) as? T
}

fun EntityEdit.remove(type: KClass<out Component>): EntityEdit {
    return remove(type.java)
}

/*
    Generics
 */

fun String.wrap(maxLength: Int = 45): List<String> {
    return WordUtils.wrap(this, maxLength).split(System.lineSeparator())
}