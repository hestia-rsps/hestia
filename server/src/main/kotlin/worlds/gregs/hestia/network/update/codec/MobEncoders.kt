package worlds.gregs.hestia.network.update.codec

import worlds.gregs.hestia.network.update.codec.encode.*
import worlds.gregs.hestia.network.update.codec.encode.mob.*

/**
 * Container for all mob block encoders
 */
class MobEncoders : UpdateBlockCodec() {

    init {
        bind(AnimationBlockEncoder(true))
        bind(BatchAnimationBlockEncoder(true))
        bind(ColourOverlayBlockEncoder(true))
        bind(CombatLevelBlockEncoder())
        bind(FaceDirectionMobBlockEncoder())
        bind(ForceMovementBlockEncoder(true))
        bind(GraphicMobBlockEncoder())
        bind(HitsBlockEncoder(true))
        bind(ModelChangeMobBlockEncoder())
        bind(NameBlockEncoder())
        bind(TimeBarBlockEncoder(true))
        bind(TransformBlockEncoder())
        bind(WatchEntityBlockEncoder(true))
        bind(ForceChatBlockEncoder())
    }

}