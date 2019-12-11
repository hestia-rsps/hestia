package worlds.gregs.hestia.api.movement

interface CollisionDetection {

    /**
     * Checks whether any objects at [x], [y], [plane] collide with [mask] type
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @param plane The plane coordinate to check
     * @param mask The masks of object types to collide with
     * @param crowd Whether should collide with entities
     */
    fun collides(x: Int, y: Int, plane: Int, mask: Int, crowd: Boolean): Boolean

}