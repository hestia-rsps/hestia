package worlds.gregs.hestia.services

import com.artemis.*
import com.artemis.Aspect
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

fun WorldConfigurationBuilder.dependsOn(vararg clazz: KClass<out Component>): WorldConfigurationBuilder {
    return dependsOn(*clazz.map { it.java }.toTypedArray())
}

//fun Aspect.Builder.all(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
//    return all(*clazz.map { it.java }.toTypedArray())
//}

fun Aspect.Builder.one(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return one(*clazz.map { it.java }.toTypedArray())
}

fun Aspect.Builder.exclude(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return exclude(*clazz.map { it.java }.toTypedArray())
}

//fun ArchetypeBuilder.add(vararg clazz: KClass<out Component>): ArchetypeBuilder {
//    return add(*clazz.map { it.java }.toTypedArray())
//}

fun <T : Component> Entity.getComponent(type: KClass<T>): T? {
    val tf = world.componentManager.typeFactory
    return getComponent(tf.getTypeFor(type.java)) as? T
}

fun <T : Component> EntityEdit.toggle(component: T): EntityEdit {
    return if(entity.getComponent(component::class) == null) add(component) else remove(component)
}

fun EntityEdit.remove(type: KClass<out Component>): EntityEdit {
    return remove(type.java)
}

/*
    Generics
 */

//fun String.wrap(maxLength: Int = 45): List<String> {
//    return WordUtils.wrap(this, maxLength).split(System.lineSeparator())
//}