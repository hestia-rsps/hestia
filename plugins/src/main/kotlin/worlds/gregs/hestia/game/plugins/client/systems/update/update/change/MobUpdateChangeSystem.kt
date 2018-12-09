package worlds.gregs.hestia.game.plugins.client.systems.update.update.change

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BaseMobSyncSystem
import worlds.gregs.hestia.game.update.DisplayFlag

class MobUpdateChangeSystem : BaseMobSyncSystem() {

    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun local(entityId: Int, local: Int, type: DisplayFlag?, update: Boolean) {
        if(type == DisplayFlag.REMOVE) {
            iterator.remove()
        }
    }

    override fun global(entityId: Int, global: Int, type: DisplayFlag?, update: Boolean) {
        if(type != null) {
            viewportMapper.get(entityId).addLocalMob(global)
        }
    }

}