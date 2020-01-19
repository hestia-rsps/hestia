package worlds.gregs.hestia.network.update.codec

import worlds.gregs.hestia.network.update.codec.encode.*
import worlds.gregs.hestia.network.update.codec.encode.npc.*

/**
 * Container for all npc block encoders
 */
class NpcEncoders : UpdateBlockCodec() {

    init {
        bind(AnimationBlockEncoder(true))
        bind(BatchAnimationBlockEncoder(true))
        bind(ColourOverlayBlockEncoder(true))
        bind(CombatLevelBlockEncoder())
        bind(FaceDirectionNpcBlockEncoder())
        bind(ForceMovementBlockEncoder(true))
        bind(GraphicNpcBlockEncoder())
        bind(HitsBlockEncoder(true))
        bind(ModelChangeNpcBlockEncoder())
        bind(NameBlockEncoder())
        bind(TimeBarBlockEncoder(true))
        bind(TransformBlockEncoder())
        bind(WatchEntityBlockEncoder(true))
        bind(ForceChatBlockEncoder())
    }

}