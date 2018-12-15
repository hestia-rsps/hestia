package worlds.gregs.hestia.game.plugins.map.systems

import worlds.gregs.hestia.game.plugins.movement.systems.calc.MovementTester

interface CollisionTestInterface {
    var clip: MovementTester.ClippingBuilderTester?
    var start: Pair<Int, Int>
    var size: Pair<Int, Int>

    fun singleCanvas() {
        //Given
        canvas(0 until 5, 0 until 5)

        //When
        start(1, 1)
        size(1, 1)
    }

    fun squareCanvas() {
        //Given
        canvas(0 until 6, 0 until 6)

        //When
        start(2, 2)
        size(2, 2)
    }

    fun rectCanvas() {
        //Given
        canvas(0 until 6, 0 until 9)

        //When
        start(2, 3)
        size(2, 3)
    }

    fun thinCanvas() {
        //Given
        canvas(0 until 6, 0 until 9)

        //When
        start(1, 3)
        size(1, 3)
    }

    fun canvas(width: IntRange, height: IntRange) {
        if(this is MovementTester) {
            clip = build {
                crop(width, height)
                blockAll()
                clear(width, height)
            }
        }
    }

    fun print() {
        clip?.print()
    }

    fun rotate(rotation: Int) {
        clip?.rotate(rotation)
    }

    fun block(x: Int, y: Int) {
        clip?.block(x, y)
    }

    fun block(x: IntRange, y: IntRange) {
        clip?.block(x, y)
    }

    fun block(x: Int, y: IntRange) {
        clip?.block(x, y)
    }

    fun block(x: IntRange, y: Int) {
        clip?.block(x, y)
    }

    fun start(x: Int, y: Int) {
        start = Pair(x, y)
    }

    fun size(width: Int, height: Int) {
        size = Pair(width, height)
    }
}