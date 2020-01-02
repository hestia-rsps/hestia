package worlds.gregs.hestia.content.dialogue


internal class DialogDSLTest {

    /*@Test
    fun `Test herblore dsl`() {
        val target = 0
        val entity = 0
        dialogue {
            val type = target map Type::class
            val inv = target map Inventory::class

        }
    }

    @Test
    fun `Test pickup dsl`() {
        val world = 0
        val entity = 0
        val target = 0
        var choice: Int
        dialogue {
            entity interact target
            val amount = target map Amount::class
            val type = target map Type::class

            val item = Item(type, amount)

            val inventory = target map Inventory::class
            val inventorySystem = world system InventorySystem::class


            when(inventorySystem.add(entity, ItemType.get(type.id)!!, amount.amount)) {
                Success -> {
                    val entityPosition = entity map Position::class
                    val targetPosition = target map Position::class
                    if(!entityPosition.same(targetPosition)) {
                        entity animate 3863
                    }

                    val mapper: ComponentMapper<Position>? = null

                    val position: (Int) -> Position = {
                        //Here lies component mapper
                        Position()
                    }

                    if(!position(entity).same(position(target)))

                    world delete target
                }
                ItemResult.NoEmptySlots, ItemResult.Full -> {
                    entity message "Your inventory is too full."
                    entity drop item
                }
            }
        }
    }
    @Test
    fun `Test bank dsl`() {
        val entity = 0
        val target = 0
        var choice: Int
        dialogue {
            entity interact target
            target watch entity//Shouldn't this be done inside the dialogue. Does it re-occur each dialogue or is it only on the first one?

            target animation Happy dialogue "Good day. How may I help you?"
            target animate Dance

            choice = option("""
                What would you like to do?
                I'd like to access my bank account, please.
                I'd like to check my PIN settings.
                I'd like to see my collection box.
                What is this place?
            """)

            when (choice) {
                FIRST -> entity.openBank()
                SECOND -> entity openInterface "PIN"
                THIRD -> entity openInterface "Collection"
                FOURTH -> {
                    target dialogue """
                        This is a branch of the Bank of RuneScape. We have
                        branches in many towns.
                    """

                    choice = options("""
                        And what do you do?
                        Didn't you used to be called the Bank of Varrock?
                    """)

                    when(choice) {
                        FIRST -> target dialogue """
                            We will look after your items and money for you.
                            Leave your valuables with us if you want to keep them
                            safe.
                        """
                        SECOND -> target dialogue """
                            Yes we did, but people kept on coming into our
                            branches outside of Varrock and telling us that our
                            signs were wrong. They acted as if we didn't know
                            what town we were in or something.
                        """
                    }
                }
            }
        }
    }*/

}