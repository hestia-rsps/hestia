package worlds.gregs.hestia.script

import com.artemis.*
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.game
import worlds.gregs.hestia.game.plugin.ScriptLoader.listeners
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.isAccessible
import kotlin.script.templates.standard.ScriptTemplateWithArgs

/**
 * Class for testing scripts by mocking injections
 */
@ExtendWith(MockKExtension::class)
abstract class ScriptMock<T : ScriptTemplateWithArgs>(private val aspect: Aspect.Builder? = null) : ScriptLoader {

    private val namedInjections = HashMap<String, Any>()
    val typedInjections = HashMap<Class<*>, Any>()
    val mappers = HashMap<Class<*>, ComponentMapper<*>>()
    protected lateinit var script: T
    @RelaxedMockK
    protected lateinit var world: World
    @RelaxedMockK
    protected lateinit var es: EventSystem

    @BeforeEach
    open fun setup() {
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt", "worlds.gregs.hestia.core.action.logic.ActionsKt")
        listeners.clear()
        //Load script
        script = if (aspect != null) {
            loadScriptClass().getConstructor(Aspect.Builder::class.java).newInstance(aspect) as T
        } else {
            loadScript()!!
        }
        every { world.aspectSubscriptionManager } returns mockk(relaxed = true)

        setSystem(EventSystem::class.java, es)
        //Set world
        game = world
    }

    fun <T : BaseSystem> setSystem(clazz: KClass<T>, system: T) = setSystem(clazz.java, system)

    fun <T : BaseSystem> setSystem(clazz: Class<T>, system: T) {
        every { world.getSystem(clazz) } returns system
    }

    fun <T : Component> getMapper(type: KClass<T>) = getMapper(type.java)

    fun <T : Component> getMapper(type: Class<T>) : ComponentMapper<T> {
        return mappers.getOrPut(type) {
            val mock = mockk<ComponentMapper<T>>(relaxed = true)
            every { world.getMapper(type) } returns mock
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