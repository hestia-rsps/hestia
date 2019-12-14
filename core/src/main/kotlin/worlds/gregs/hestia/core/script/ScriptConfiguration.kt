package worlds.gregs.hestia.core.script

import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm

object ScriptConfiguration : ScriptCompilationConfiguration({
    defaultImports("worlds.gregs.hestia.*",

            "worlds.gregs.hestia.core.display.dialogue.logic.systems.types.*",
            "worlds.gregs.hestia.core.script.dsl.*",
            "worlds.gregs.hestia.core.script.*",

            "worlds.gregs.hestia.core.task.api.*",
            "worlds.gregs.hestia.core.task.model.*",
            "worlds.gregs.hestia.core.task.model.events.*",
            "worlds.gregs.hestia.core.task.api.event.target.*",
            "worlds.gregs.hestia.core.entity.item.container.model.*",
            "worlds.gregs.hestia.core.task.model.context.*",
            "worlds.gregs.hestia.core.entity.item.container.logic.*",

            "kotlinx.coroutines.*",
            "kotlin.*",
            "kotlinx.*",

            "net.mostlyoriginal.api.event.common.*",
            "net.mostlyoriginal.api.*"
    )
    jvm {
        dependenciesFromClassloader(wholeClasspath = true)
    }
    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
})