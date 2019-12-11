package worlds.gregs.hestia.scripts

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.World
import com.artemis.injection.CachedInjector
import com.artemis.injection.FieldHandler
import com.artemis.utils.reflect.Field
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import worlds.gregs.hestia.core.scripts.ScriptBuilder
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * Class for testing scripts by mocking injections
 */
abstract class ScriptTesterMock<T : ScriptBuilder>(private val aspect: Aspect.Builder? = null) : ScriptLoader {

    private val namedInjections = HashMap<String, Any>()
    private val typedInjections = HashMap<Class<*>, Any>()
    protected lateinit var script: T
    protected lateinit var world: World

    @BeforeEach
    fun setup() {
        //Load script
        script = if (aspect != null) {
            loadScriptClass().getConstructor(Aspect.Builder::class.java).newInstance(aspect) as T
        } else {
            loadScript()!!
        }

        //Clear injection list
        namedInjections.clear()
        typedInjections.clear()

        //Add defaults
        inject(ComponentMapper::class, mock())
        world = mock()
        inject(World::class, world)
        whenever(world.aspectSubscriptionManager).thenReturn(mock())

        val fieldHandler = mock<FieldHandler>()
        //Might need to be expanded at some point to handle other injection types
        whenever(fieldHandler.resolve(any(), any(), any())).then {
            val field = it.getArgumentAt(2, Field::class.java)
            return@then typedInjections[field.type] ?: namedInjections[field.name]
        }

        //Inject fields
        val injector = CachedInjector()
        injector.setFieldHandler(fieldHandler)
        injector.inject(script)

        //Set world
        val setWorld = script::class.functions.first { it.name == "setWorld" }
        callUnsafe(setWorld, script, world)
        //Initialise
        val initialise = script::class.memberFunctions.first { it.name == "initialize" }
        callUnsafe(initialise, script)

    }

    fun inject(fieldName: String, any: Any): Any {
        namedInjections[fieldName] = any
        return any
    }

    fun <T : Any> inject(type: KClass<T>, any: T): T {
        typedInjections[type.java] = any
        return any
    }

    companion object {
        private fun callUnsafe(function: KFunction<*>, vararg args: Any?) {
            val accessible = function.isAccessible
            function.isAccessible = true
            function.call(*args)
            function.isAccessible = accessible
        }
    }
}