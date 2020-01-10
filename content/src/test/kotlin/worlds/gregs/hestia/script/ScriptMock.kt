package worlds.gregs.hestia.script

import com.artemis.*
import com.artemis.injection.CachedInjector
import com.artemis.injection.FieldHandler
import com.artemis.utils.reflect.Field
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.script.ScriptBuilder
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * Class for testing scripts by mocking injections
 */
@ExtendWith(MockKExtension::class)
abstract class ScriptMock<T : ScriptBuilder>(private val aspect: Aspect.Builder? = null) : ScriptLoader {

    private val namedInjections = HashMap<String, Any>()
    val typedInjections = HashMap<Class<*>, Any>()
    val mappers = HashMap<Class<*>, ComponentMapper<*>>()
    protected lateinit var script: T
    @RelaxedMockK
    protected lateinit var world: World
    @RelaxedMockK
    protected lateinit var es: EventSystem

    @BeforeEach
    fun setup() {
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt", "worlds.gregs.hestia.core.action.logic.ActionsKt")
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
        inject(world)
        setSystem(es)
        every { world.aspectSubscriptionManager } returns mockk(relaxed = true)

        val fieldHandler = mockk<FieldHandler>()
        //Might need to be expanded at some point to handle other injection types
        every { fieldHandler.resolve(any(), any(), any()) } answers {
            val field = arg<Field>(2)
            typedInjections[field.type] ?: namedInjections[field.name]
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

    inline fun <reified T : Any> inject(any: T): T {
        typedInjections[any::class.java] = any
        return any
    }

    fun <T : BaseSystem> setSystem(system: T) {
        every { world.getSystem(system::class.java) } returns system
    }

    fun <T : Component> getMapper(type: KClass<T>) : ComponentMapper<T> {
        return mappers.getOrPut(type.java) {
            val mock = mockk<ComponentMapper<T>>(relaxed = true)
            every { world.getMapper(type.java) } returns mock
            mock
        } as ComponentMapper<T>
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