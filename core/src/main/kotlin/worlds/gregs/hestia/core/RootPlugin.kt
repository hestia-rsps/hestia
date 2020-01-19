package worlds.gregs.hestia.core

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.action.ActionPlugin
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
import worlds.gregs.hestia.core.task.TaskPlugin
import worlds.gregs.hestia.core.world.collision.CollisionPlugin
import worlds.gregs.hestia.core.world.land.LandPlugin
import worlds.gregs.hestia.core.world.map.MapPlugin
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.region.RegionPlugin
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.PluginBase

class RootPlugin : PluginBase(), Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.dependsOn(TaskPlugin::class.java)
        b.dependsOn(BotPlugin::class.java)
        b.dependsOn(ClientPlugin::class.java)
        b.dependsOn(CollisionPlugin::class.java)
        b.dependsOn(CachePlugin::class.java)
        b.dependsOn(DialoguePlugin::class.java)
        b.dependsOn(EntityPlugin::class.java)
        b.dependsOn(InterfacePlugin::class.java)
        b.dependsOn(RequestPlugin::class.java)
        b.dependsOn(VariablePlugin::class.java)
        b.dependsOn(LandPlugin::class.java)
        b.dependsOn(MapPlugin::class.java)
        b.dependsOn(NpcPlugin::class.java)
        b.dependsOn(ItemPlugin::class.java)
        b.dependsOn(ActionPlugin::class.java)
        b.dependsOn(MovementPlugin::class.java)
        b.dependsOn(ObjectPlugin::class.java)
        b.dependsOn(PlayerPlugin::class.java)
        b.dependsOn(RegionPlugin::class.java)
    }

}