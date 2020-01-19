package worlds.gregs.hestia.core.display.update.logic.block

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.update.logic.block.factory.*
import worlds.gregs.hestia.core.display.update.logic.block.factory.npc.*
import worlds.gregs.hestia.core.display.update.logic.block.factory.player.*
import worlds.gregs.hestia.game.plugin.Plugin

class BlockPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        //Player update blocks
        b.with(
                BatchAnimationBlockFactory(0x8000),
                AnimationBlockFactory(0x40),
                GraphicThreeBlockFactory(0x40000),
                ColourOverlayBlockFactory(0x20000),
                MoveTypePlayerBlockFactory(0x200),
                TimeBarBlockFactory(0x2000),
                GraphicFourBlockFactory(0x80000),
                ClanMemberPlayerBlockFactory(0x100000),
                HitsBlockFactory(0x4),
                UnknownPlayerBlockFactory(0x10000),
                AppearancePlayerBlockFactory(0x8),
                ForceChatBlockFactory(0x4000),
                MiniMapPlayerBlockFactory(0x400),
                MovementPlayerBlockFactory(0x1),
                WatchEntityBlockFactory(0x10),
                ForceMovementBlockFactory(0x1000),
                FaceDirectionPlayerBlockFactory(0x20),
                GraphicOneBlockFactory(0x2),
                GraphicTwoBlockFactory(0x100)
        )

        //Npc update blocks
        b.with(
                object : GraphicThreeBlockFactory(0x100000, true) {},
                object : WatchEntityBlockFactory(0x1, true) {},
                object : GraphicFourBlockFactory(0x20000, true) {},
                object : HitsBlockFactory(0x40, true) {},
                object : TimeBarBlockFactory(0x100, true) {},
                NameNpcBlockFactory(0x40000),
                TransformNpcBlockFactory(0x20),
                object : ForceChatBlockFactory(0x2, true) {},
                FaceDirectionNpcBlockFactory(0x8),
                CombatLevelNpcBlockFactory(0x80000),
                //0x2000 - Weapon hidden/rotation
                //0x10000 - Model change (not sure it actually does anything)
                object : ForceMovementBlockFactory(0x400, true) {},
                object : AnimationBlockFactory(0x10, true) {},
                ModelChangeNpcBlockFactory(0x800),
                object : BatchAnimationBlockFactory(0x4000, true) {},
                object : GraphicTwoBlockFactory(0x1000, true) {},
                object : GraphicOneBlockFactory(0x4, true) {},
                object : ColourOverlayBlockFactory(0x200, true) {}
        )
    }

}