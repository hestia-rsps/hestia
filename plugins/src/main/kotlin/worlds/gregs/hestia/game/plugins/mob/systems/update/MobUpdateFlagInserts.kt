package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.MobUpdateFlagSystem
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import worlds.gregs.hestia.game.plugins.mob.component.update.MobModelChange
import worlds.gregs.hestia.game.plugins.mob.component.update.UpdateCombatLevel
import worlds.gregs.hestia.game.plugins.mob.component.update.UpdateDisplayName
import worlds.gregs.hestia.game.plugins.player.component.update.appearance.CombatLevel
import worlds.gregs.hestia.network.update.mob.MobCombatLevelMask
import worlds.gregs.hestia.network.update.mob.MobModelChangeMask
import worlds.gregs.hestia.network.update.mob.MobNameMask
import worlds.gregs.hestia.services.Aspect

class MobUpdateFlagInserts : PassiveSystem() {

    private lateinit var flagSystem: MobUpdateFlagSystem

    //Flags
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>
    private lateinit var modelChangeMapper: ComponentMapper<MobModelChange>

    override fun initialize() {
        super.initialize()

        //Name
        flagSystem.insertAfter(0x100, flagSystem.create(0x40000, Aspect.all(Renderable::class, UpdateDisplayName::class), MobNameMask(displayNameMapper), true))
        //Combat level
        flagSystem.insertAfter(0x8, flagSystem.create(0x80000, Aspect.all(Renderable::class, UpdateCombatLevel::class), MobCombatLevelMask(combatLevelMapper), true))
        //Model change
        flagSystem.insertAfter(0x10, flagSystem.create(0x800, Aspect.all(Renderable::class, MobModelChange::class), MobModelChangeMask(modelChangeMapper)))
    }
}