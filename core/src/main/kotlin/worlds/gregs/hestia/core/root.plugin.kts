package worlds.gregs.hestia.core

import worlds.gregs.hestia.core.display.client.ClientPlugin
import worlds.gregs.hestia.core.display.dialogue.DialoguePlugin
import worlds.gregs.hestia.core.display.interfaces.InterfacePlugin
import worlds.gregs.hestia.core.display.request.RequestPlugin
import worlds.gregs.hestia.core.display.variable.VariablePlugin
import worlds.gregs.hestia.core.entity.`object`.ObjectPlugin
import worlds.gregs.hestia.core.entity.bot.BotPlugin
import worlds.gregs.hestia.core.entity.entity.EntityPlugin
import worlds.gregs.hestia.core.entity.item.ItemPlugin
import worlds.gregs.hestia.core.entity.npc.NpcPlugin
import worlds.gregs.hestia.core.entity.player.PlayerPlugin
import worlds.gregs.hestia.core.task.Task_plugin
import worlds.gregs.hestia.core.world.collision.CollisionPlugin
import worlds.gregs.hestia.core.world.land.LandPlugin
import worlds.gregs.hestia.core.world.map.MapPlugin
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.region.RegionPlugin
import worlds.gregs.hestia.core.action.ActionPlugin

setup {
    dependsOn(Task_plugin::class.java)
    dependsOn(BotPlugin::class.java)
    dependsOn(ClientPlugin::class.java)
    dependsOn(CollisionPlugin::class.java)
    dependsOn(CachePlugin::class.java)
    dependsOn(DialoguePlugin::class.java)
    dependsOn(EntityPlugin::class.java)
    dependsOn(InterfacePlugin::class.java)
    dependsOn(RequestPlugin::class.java)
    dependsOn(VariablePlugin::class.java)
    dependsOn(LandPlugin::class.java)
    dependsOn(MapPlugin::class.java)
    dependsOn(NpcPlugin::class.java)
    dependsOn(ItemPlugin::class.java)
    dependsOn(ActionPlugin::class.java)
    dependsOn(MovementPlugin::class.java)
    dependsOn(ObjectPlugin::class.java)
    dependsOn(PlayerPlugin::class.java)
    dependsOn(RegionPlugin::class.java)
}

init { world, dispatcher ->
}