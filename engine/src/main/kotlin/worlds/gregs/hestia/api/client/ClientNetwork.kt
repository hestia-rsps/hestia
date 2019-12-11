package worlds.gregs.hestia.api.client

import io.netty.channel.Channel
import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class ClientNetwork : PassiveSystem() {
    /**
     * Sets up the entities client network
     * @param entityId The entity to link
     * @param channel The client connection channel
     */
    abstract fun setup(entityId: Int, channel: Channel)
}