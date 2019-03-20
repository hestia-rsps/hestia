package worlds.gregs.hestia.network.update.codec

import worlds.gregs.hestia.network.update.codec.encode.*
import worlds.gregs.hestia.network.update.codec.encode.player.*

/**
 * Container for all player block encoders
 */
class PlayerEncoders : UpdateBlockCodec() {

    init {
        bind(AnimationBlockEncoder(false))
        bind(AppearanceBlockEncoder())
        bind(BatchAnimationBlockEncoder(false))
        bind(ClanMemberBlockEncoder())
        bind(ColourOverlayBlockEncoder(false))
        bind(FaceDirectionPlayerBlockEncoder())
        bind(ForceMovementBlockEncoder(false))
        bind(GraphicPlayerBlockEncoder())
        bind(HitsBlockEncoder(false))
        bind(MiniMapPlayerBlockEncoder())
        bind(MovementBlockEncoder())
        bind(MoveTypeBlockEncoder())
        bind(TimeBarBlockEncoder(false))
        bind(UnknownPlayerBlockEncoder())
        bind(WatchEntityBlockEncoder(false))
        bind(ForceChatBlockEncoder())
    }

}