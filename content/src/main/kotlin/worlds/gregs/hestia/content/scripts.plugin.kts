package worlds.gregs.hestia.content

import worlds.gregs.hestia.content.command.*
import worlds.gregs.hestia.content.interaction.`object`.Counter_script
import worlds.gregs.hestia.content.interaction.item.Pickup_script
import worlds.gregs.hestia.content.interaction.mob.Banker_script
import worlds.gregs.hestia.content.interaction.mob.Hans_script
import worlds.gregs.hestia.content.interaction.player.Follow_script
import worlds.gregs.hestia.content.interaction.player.Trade_script
import worlds.gregs.hestia.content.interaction.player.ReqAssist_script

val scripts = listOf(ReqAssist_script(), Follow_script(), Trade_script(), Item_script(), Pickup_script(), Test_script(), Counter_script(), Banker_script(), Hans_script(), Appearance_script(), BotControl_script(), EntityCount_script(), Interface_script(), Run_script(), Teleport_script())

setup {
    scripts.forEach {
        it.build(this)
    }
}

init { world, dispatcher ->
    scripts.forEach {
        it.build(world, dispatcher)
    }
}