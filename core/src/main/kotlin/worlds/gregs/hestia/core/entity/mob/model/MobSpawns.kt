package worlds.gregs.hestia.core.entity.mob.model

import worlds.gregs.hestia.core.display.update.model.Direction

enum class MobSpawns(val id: Int, val x: Int, val y: Int, val plane: Int = 0, val direction: Direction = Direction.NONE) {
    BANKER_1(553, 3096, 3491, direction = Direction.WEST),
    BANKER_2(553, 3096, 3493, direction = Direction.WEST),
    BANKER_3(553, 3096, 3489, direction = Direction.WEST),
    BANKER_4(553, 3097, 3494, direction = Direction.NORTH),
    DORIS(3381, 3081, 3495),
    JEFFERY(637, 3106, 3500),
    MUGGER(175, 3082, 3494),
    SHOP_ASSISTANT(529, 3694, 3461),
    MR_EX(3609, 3686, 3468),
    MAN_1(2, 3101, 3508),
    MAN_2(3, 3091, 3509),
    MAN_3(3, 3100, 3511),
    SHOPKEEPER(3, 3081, 3509),
    HANS(0, 3087, 3499),
    MUSICIAN(29, 3090, 3492, direction = Direction.WEST),
}