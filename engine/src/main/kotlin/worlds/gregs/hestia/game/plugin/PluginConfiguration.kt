package worlds.gregs.hestia.game.plugin

import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.fileExtension

object PluginConfiguration : ScriptCompilationConfiguration({
    defaultImports("worlds.gregs.hestia.core.plugins.*",
            "worlds.gregs.hestia.artemis.*",
            "net.mostlyoriginal.api.*")
    fileExtension("plugin.kts")
})