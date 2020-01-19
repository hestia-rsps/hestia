package worlds.gregs.hestia.core.task

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.task.logic.systems.*
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.PluginBase

class TaskPlugin : PluginBase(), Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(TicksSystem(), InterfaceCloseSystem(), WindowCloseSystem())
        b.with(Plugin.TASK_PROCESS_PRIORITY, TaskSystem())
        b.with(Plugin.POST_TASK_PROCESS_PRIORITY, ClearTaskSystem())
        b.with(Plugin.POST_SHIFT_PRIORITY, RouteSystem(), WithinRangeSystem())
    }

}