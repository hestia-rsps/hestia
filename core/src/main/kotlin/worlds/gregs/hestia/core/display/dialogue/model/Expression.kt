package worlds.gregs.hestia.core.display.dialogue.model

/**
 * Dialogue chat animations
 */
object Expression {

    /*
        Sad
     */
    const val Sad = 9760
    const val Dwell = 9757//till 9759 - Talking sad then look down (Sad)
    const val Teary = 9769//till 9772 - Talking eyebrows down quivering lips (Concerned/Uncertain/about to cry)
    const val Upset = 9761//till 9764 - Talking sad then slight shake of head (Sad)
    const val Crying = 9768
    const val Wailing = 9765//till 9767 - Crying & wailing (Sad)

    /*
        Scared
     */
    const val Afraid = 9773//till 9776 - Shocked talking while looking south/south-west
    const val Shock = 9750//till 9752 - Head turning talking then jaw drop (Shock)
    const val Disbelief = 9753
    const val Scared = 9777//till 9780 - Shocked looking south

    /*
        Anger
     */
    const val Angry = 9781//till 9784 - Angry gritting teeth
    const val Furious = 9785//till 9788 - Angry talking through gritted teeth
    const val Mad = 9789//till 9792 - Angry shouting

    /*
        Regular
     */
    const val Talking = 9803//Same as 9810
    const val Delayed = 9806//till 9809 - Blink then talk
    const val Think = 9830//Talking eyebrow raised
    const val Suspicious = 9836//till 9839 - Looking side to side while talking (Suspicious)

    /*
        Fun
     */
    const val Cheerful = 9843//till 9846 - Talking slight head rock side to side
    const val Laugh = 9840//Chuckle
    const val Laughing = 9851//till 9854 - Laughing head nodding
    const val Hysterics = 9841//Laughing head role

    /*
        Binary
     */
    const val Agree = 9847//till 9850 - Talking eyebrows raised nodding
    const val Disagree = 9814//Shake head
    const val Uncertain = 9811//Shake head then look up (disagree then think)
    const val Disregard = 9827//till 9829 - Talking eyebrow raised then look down (Uncertain/Disregard)
    const val Disdain = 9832//till 8734 - Looks down then roles eyes (Disapproval/Disregard)


    /*
        Quiet
     */
    const val Silent = 9804//Blinking
    const val EyesClosed = 9877
    const val Asleep = 9802//Sleeping head tilted west

    /*
        Silly
     */
    const val Conflicted = 9835//Rocking head expression changing from sad to happy
    const val Chicken = 9793//Head banging
    const val Goofy = 9878//Yawn eyes role stick tongue out
    const val EvilLaugh = 9842//Overly dramatic evil laugh
}