public class Card {
	//the data members
	//suit can be either:
	//"Hearts", "Clubs", "Spades", or "Diamonds"
    String suit;
    //value can be anywhere from 1 - 13
    //Ace = 1, Jack = 11
    //Queen = 12, King = 13
    //Otherwise it's just the number of the card
    int value;
    
    //class constructor
    Card(String theSuit, int theValue) {
        this.suit = theSuit;
        this.value = theValue;
    }
}
