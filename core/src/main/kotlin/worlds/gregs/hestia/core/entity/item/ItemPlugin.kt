package worlds.gregs.hestia.core.entity.item

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.floor.logic.creation.FloorItemCreation
import worlds.gregs.hestia.core.entity.item.floor.logic.creation.FloorItemSpawnCreation
import worlds.gregs.hestia.core.entity.item.floor.logic.systems.*
import worlds.gregs.hestia.game.plugin.Plugin

class ItemPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(Plugin.UPDATE_SYNC_PRIORITY, FloorItemSyncSystem())
        b.with(FloorItemCreation(), FloorItemSpawnCreation(true), FloorItemSystem(), PrivateSystem(), PublicSystem(), FloorItemSpawnPointSystem())
        b.with(Plugin.INTERFACE_PRIORITY, ContainerSystem())
    }

}