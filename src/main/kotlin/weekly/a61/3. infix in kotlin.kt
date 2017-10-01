package weekly.a61

enum class Suit {
    HEARTS,
    SPADES,
    CLUBS,
    DIAMONDS
}

enum class Rank {
    TWO, THREE, FOUR, FIVE,
    SIX, SEVEN, EIGHT, NINE,
    TEN, JACK, QUEEN, KING, ACE;

    infix fun of(suit: Suit) = Card(this, suit)
}

data class Card(val rank: Rank, val suit: Suit)

fun main(args: Array<String>) {
    val card = Rank.QUEEN of Suit.HEARTS
}