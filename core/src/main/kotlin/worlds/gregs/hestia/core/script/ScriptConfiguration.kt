package worlds.gregs.hestia.core.script

import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports

object ScriptConfiguration : ScriptCompilationConfiguration({
    defaultImports("worlds.gregs.hestia.api.*",
            "worlds.gregs.hestia.core.plugins.bot.*",
            "worlds.gregs.hestia.core.client.*",
            "worlds.gregs.hestia.core.world.collision.*",
            "worlds.gregs.hestia.core.display.dialogue.*",
            "worlds.gregs.hestia.core.plugins.queue.*",
            "worlds.gregs.hestia.core.entity.*",
            "worlds.gregs.hestia.core.world.land.*",
            "worlds.gregs.hestia.core.world.map.*",
            "worlds.gregs.hestia.core.plugins.mob.*",
            "worlds.gregs.hestia.core.world.movement.*",
            "worlds.gregs.hestia.core.entity.object.*",
            "worlds.gregs.hestia.core.entity.player.*",
            "worlds.gregs.hestia.core.world.region.*",
            "worlds.gregs.hestia.core.display.widget.*",

            "worlds.gregs.hestia.api.*",
            "worlds.gregs.hestia.artemis.events.*",
            "worlds.gregs.hestia.game.*",
            "worlds.gregs.hestia.game.archetypes.*",
            "worlds.gregs.hestia.game.client.*",
            "worlds.gregs.hestia.game.entity.*",
            "worlds.gregs.hestia.game.map.*",
            "worlds.gregs.hestia.game.queue.*",
            "worlds.gregs.hestia.services.*",

            "kotlin.*",
            "kotlinx.*",
            "import kotlinx.coroutines.*",
            "net.mostlyoriginal.api.*")
})