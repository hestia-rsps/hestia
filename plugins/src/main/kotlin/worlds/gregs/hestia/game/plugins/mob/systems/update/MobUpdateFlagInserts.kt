package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.api.client.MobUpdateFlags
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.entity.components.update.CombatLevel
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import worlds.gregs.hestia.game.plugins.mob.component.update.ModelChange
import worlds.gregs.hestia.game.plugins.mob.component.update.UpdateCombatLevel
import worlds.gregs.hestia.game.plugins.mob.component.update.UpdateDisplayName
import worlds.gregs.hestia.network.update.mob.MobCombatLevelMask
import worlds.gregs.hestia.network.update.mob.MobModelChangeMask
import worlds.gregs.hestia.network.update.mob.MobNameMask
import worlds.gregs.hestia.services.Aspect

class MobUpdateFlagInserts : PassiveSystem() {

    private var flags: MobUpdateFlags? = null

    //Flags
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>
    private lateinit var modelChangeMapper: ComponentMapper<ModelChange>

    override fun initialize() {
        super.initialize()

        val flags = flags ?: return

        //Name
        flags.insertAfter(0x100, flags.create(0x40000, Aspect.all(Renderable::class, UpdateDisplayName::class), MobNameMask(displayNameMapper), true))
        //Combat level
        flags.insertAfter(0x8, flags.create(0x80000, Aspect.all(Renderable::class, UpdateCombatLevel::class), MobCombatLevelMask(combatLevelMapper), true))
        //Model change
        flags.insertAfter(0x10, flags.create(0x800, Aspect.all(Renderable::class, ModelChange::class), MobModelChangeMask(modelChangeMapper)))
    }
}