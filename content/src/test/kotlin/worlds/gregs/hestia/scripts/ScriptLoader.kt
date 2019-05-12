package worlds.gregs.hestia.scripts

/**
 * Loads script by matching test class package and name
 */
interface ScriptLoader {

    fun loadScriptClass(): Class<*> {
        val clazz = this::class.java
        val scriptPackage = "${clazz.packageName}.${clazz.simpleName.substring(0, clazz.simpleName.length - 10)}_script"
        return Class.forName(scriptPackage)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> loadScript(): T? {
        val clazz = this::class.java
        val scriptPackage = "${clazz.packageName}.${clazz.simpleName.substring(0, clazz.simpleName.length - 10)}_script"
        return Class.forName(scriptPackage).getConstructor().newInstance() as? T
    }

}