package worlds.gregs.hestia.core.action.model

sealed class OptionType
sealed class OnOptionType : OptionType()
sealed class SingleOptionType : OptionType()

object NpcOption : SingleOptionType()
object ObjectOption : SingleOptionType()
object PlayerOption : SingleOptionType()
object FloorItemOption : SingleOptionType()

object InterfaceOption : OptionType()

object InterfaceOnNpc : OnOptionType()
object InterfaceOnObject : OnOptionType()
object InterfaceOnPlayer : OnOptionType()
object InterfaceOnFloorItem : OnOptionType()
object InterfaceOnInterface : OnOptionType()