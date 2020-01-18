package worlds.gregs.hestia.core

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.config.systems.*
import worlds.gregs.hestia.service.cache.definition.systems.*

class CachePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(CacheSystem())
        //Indices
        b.with(
                AnimationDefinitionSystem(),
                BodyDefinitionSystem(),
                EnumDefinitionSystem(),
                GraphicDefinitionSystem(),
                ItemDefinitionSystem(),
                MobDefinitionSystem(),
                ObjectDefinitionSystem(),
                QuickChatOptionDefinitionSystem(),
                VarBitDefinitionSystem(),
                InterfaceDefinitionSystem(),
                WorldMapDefinitionSystem()
        )
        //Configs
        b.with(
                ClientVariableParameterDefinitionSystem(),
                HitSplatDefinitionSystem(),
                IdentityKitDefinitionSystem(),
                ItemContainerDefinitionSystem(),
                PlayerVariableParameterDefinitionSystem(),
                QuestDefinitionSystem(),
                RenderAnimationDefinitionSystem(),
                StrutDefinitionSystem(),
                WorldMapInfoDefinitionSystem()
        )
    }

}