package worlds.gregs.hestia.core.scripts

import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports

object ScriptConfiguration : ScriptCompilationConfiguration({
    defaultImports("worlds.gregs.hestia.api.*",
            "worlds.gregs.hestia.core.plugins.bot.*",
            "worlds.gregs.hestia.core.plugins.client.*",
            "worlds.gregs.hestia.core.plugins.collision.*",
            "worlds.gregs.hestia.core.plugins.dialogue.*",
            "worlds.gregs.hestia.core.plugins.queue.*",
            "worlds.gregs.hestia.core.plugins.entity.*",
            "worlds.gregs.hestia.core.plugins.land.*",
            "worlds.gregs.hestia.core.plugins.map.*",
            "worlds.gregs.hestia.core.plugins.mob.*",
            "worlds.gregs.hestia.core.plugins.movement.*",
            "worlds.gregs.hestia.core.plugins.object.*",
            "worlds.gregs.hestia.core.plugins.player.*",
            "worlds.gregs.hestia.core.plugins.region.*",
            "worlds.gregs.hestia.core.plugins.widget.*",

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