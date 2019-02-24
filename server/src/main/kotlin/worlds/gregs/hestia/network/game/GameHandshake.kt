package worlds.gregs.hestia.network.game

import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshake

class GameHandshake(dispatcher: HandshakeDispatcher) : SimpleMessageHandshake(dispatcher)