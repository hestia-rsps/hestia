package worlds.gregs.hestia.core.script.dsl.artemis

import com.artemis.Aspect
import com.artemis.Component
import kotlin.reflect.KClass

/**
 * Extensions for simplifying [Aspect] creation
 */
interface Aspects {

    /**
     * Returns an aspect where an entity must possess all of the specified
     * component types.
     * Note: Performance cost
     *
     * @param types a required component type
     * @return an aspect that can be matched against entities
     */
    fun all(vararg types: KClass<out Component>): Aspect.Builder {
        return Aspect.all(types.map { it.java })
    }

    /**
     * Returns an aspect where an entity must possess one of the specified
     * component types.
     * Note: Performance cost
     *
     * @param types one of the types the entity must possess
     * @return an aspect that can be matched against entities
     */
    fun one(vararg types: KClass<out Component>): Aspect.Builder {
        return Aspect.one(types.map { it.java })
    }

    /**
     * Excludes all of the specified component types from the aspect.
     * <p>
     * A system will not be interested in an entity that possesses one of the
     * specified exclusion component types.
     * </p>
     * Note: Performance cost
     *
     * @param types component type to exclude
     * @return an aspect that can be matched against entities
     */
    fun exclude(vararg types: KClass<out Component>): Aspect.Builder {
        return Aspect.exclude(types.map { it.java })
    }

    /**
     * Returns an aspect where an entity must possess all of the specified
     * component types.
     *
     * @param type a required component type
     * @return an aspect that can be matched against entities
     */
    infix fun Aspect.Builder.all(type: KClass<out Component>): Aspect.Builder {
        all(type.java)
        return this
    }

    /**
     * Returns an aspect where an entity must possess one of the specified
     * component types.
     *
     * @param type one of the types the entity must possess
     * @return an aspect that can be matched against entities
     */
    infix fun Aspect.Builder.one(type: KClass<out Component>): Aspect.Builder {
        one(type.java)
        return this
    }

    /**
     * Excludes all of the specified component types from the aspect.
     * <p>
     * A system will not be interested in an entity that possesses one of the
     * specified exclusion component types.
     * </p>
     *
     * @param type component type to exclude
     * @return an aspect that can be matched against entities
     */
    infix fun Aspect.Builder.exclude(type: KClass<out Component>): Aspect.Builder {
        exclude(type.java)
        return this
    }
}