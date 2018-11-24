package worlds.gregs.hestia.game.update

enum class UpdateStage {
    ADD,
    REMOVE,
    WALKING,
    RUNNING,
    MOVE,
    UPDATE,
    HEIGHT,
    REGION,
    SKIP;

    fun movementType(): Int {
        return when (this) {
            WALKING, HEIGHT -> 1
            RUNNING, REGION -> 2
            MOVE -> 3
            else -> 0
        }
    }
}