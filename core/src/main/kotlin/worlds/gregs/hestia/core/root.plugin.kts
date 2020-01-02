package worlds.gregs.hestia.core

import worlds.gregs.hestia.core.display.client.ClientPlugin
import worlds.gregs.hestia.core.display.dialogue.DialoguePlugin
import worlds.gregs.hestia.core.display.widget.WidgetPlugin
import worlds.gregs.hestia.core.entity.`object`.ObjectPlugin
import worlds.gregs.hestia.core.entity.`object`.logic.ObjectFactory
import worlds.gregs.hestia.core.entity.bot.BotPlugin
import worlds.gregs.hestia.core.entity.entity.EntityPlugin
import worlds.gregs.hestia.core.entity.item.ItemPlugin
import worlds.gregs.hestia.core.entity.mob.MobPlugin
import worlds.gregs.hestia.core.entity.player.PlayerPlugin
import worlds.gregs.hestia.core.task.Task_plugin
import worlds.gregs.hestia.core.task.tick.TickPlugin
import worlds.gregs.hestia.core.world.collision.CollisionPlugin
import worlds.gregs.hestia.core.world.land.LandPlugin
import worlds.gregs.hestia.core.world.map.MapPlugin
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.region.RegionPlugin

setup {
    dependsOn(Task_plugin::class.java)
    dependsOn(BotPlugin::class.java)
    dependsOn(ClientPlugin::class.java)
    dependsOn(CollisionPlugin::class.java)
    dependsOn(TickPlugin::class.java)
    dependsOn(CachePlugin::class.java)
    dependsOn(DialoguePlugin::class.java)
    dependsOn(EntityPlugin::class.java)
    dependsOn(WidgetPlugin::class.java)
    dependsOn(LandPlugin::class.java)
    dependsOn(MapPlugin::class.java)
    dependsOn(MobPlugin::class.java)
    dependsOn(ItemPlugin::class.java)
    dependsOn(MovementPlugin::class.java)
    dependsOn(ObjectPlugin::class.java)
    dependsOn(PlayerPlugin::class.java)
    dependsOn(RegionPlugin::class.java)
}

init { world, dispatcher ->
}