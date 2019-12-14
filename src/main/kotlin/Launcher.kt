import worlds.gregs.hestia.GameServer

/**
 * We're using the root project to launch so that both :engine & :core
 * are included in the build without any circular dependency.
 */
fun main(args: Array<String>) {
    GameServer.start(args)
}