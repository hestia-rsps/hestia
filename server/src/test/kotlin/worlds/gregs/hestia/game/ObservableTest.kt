package worlds.gregs.hestia.game

import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class ObservableTest {

    var name2: String by Delegates.vetoable("no name") { _, old, new ->
        println("$old - $new")
        false
    }

    private var name: String by Delegates.observable("no name") { _, old, new ->
        println("$old - $new")
    }

    @Test
    fun test() {
        name2 = "Carl"
        println(name2)
        name = "Carl"
        println(name)
    }



    fun t() {
    }
}