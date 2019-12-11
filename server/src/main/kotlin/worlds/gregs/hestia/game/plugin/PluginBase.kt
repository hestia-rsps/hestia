package worlds.gregs.hestia.game.plugin

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.artemis.event.ExtendedFastEventDispatcher
import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(displayName = "Artemis Plugin Base Script", fileExtension = "plugin.kts", compilationConfiguration = PluginConfiguration::class)
abstract class PluginBase : Plug {

    private var builder: (WorldConfigurationBuilder.() -> Unit)? = null
    private var init: ((World, ExtendedFastEventDispatcher) -> Unit)? = null

    fun setup(action: WorldConfigurationBuilder.() -> Unit) {
        builder = action
    }

    fun init(action: (World, ExtendedFastEventDispatcher) -> Unit) {
        init = action
    }

    override fun setup(b: WorldConfigurationBuilder) {
        builder?.invoke(b)
    }

    override fun init(world: World, dispatcher: ExtendedFastEventDispatcher) {
        init?.invoke(world, dispatcher)
    }

}