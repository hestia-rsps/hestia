package worlds.gregs.hestia.network.client

import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshake

class ClientHandshake(dispatcher: HandshakeDispatcher) : SimpleMessageHandshake(dispatcher)