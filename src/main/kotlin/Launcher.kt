import world.gregs.hestia.GameServer

/**
 * We're using the root project to launch so that both :server & :plugins
 * are included in the build without any circular dependency.
 */
fun main(args: Array<String>) {
    GameServer.start(args)
}