package worlds.gregs.hestia.core.action

import com.artemis.World

data class ActionEvent(val world: World, val entity: Int, val action: Action)