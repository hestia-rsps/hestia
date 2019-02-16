package worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.core.components.Created
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.services.send

abstract class BasePlayerUpdateSystem : BasePlayerSyncSystem(), BaseUpdateSystem {

    private lateinit var es: EventSystem
    private lateinit var createdMapper: ComponentMapper<Created>

    override fun local(entityId: Int, local: Int, type: DisplayFlag?, update: Boolean) {
        if (isLocal(type, update)) {
            update(entityId, local, packet, createdMapper, false)
        }
    }

    override fun global(entityId: Int, global: Int, type: DisplayFlag?, update: Boolean) {
        if (isGlobal(type, update)) {
            update(entityId, global, packet, createdMapper, true)
        }
    }

    override fun process(entityId: Int) {
        super.process(entityId)

        es.send(entityId, packet.build())
    }
}