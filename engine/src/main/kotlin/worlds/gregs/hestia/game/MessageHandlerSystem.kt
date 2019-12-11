package worlds.gregs.hestia.game

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.network.client.ClientMessageHandler

/**
 * Template for systems handling inbound [Message]s
 */
abstract class MessageHandlerSystem<T : Message> : PassiveSystem(), ClientMessageHandler<T>