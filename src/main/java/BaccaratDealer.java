import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
	//our list representation of the deck
	ArrayList<Card> deck;
	
	//this method populates the deck with 52 cards, in order
	public void generateDeck() {
		deck = new ArrayList<Card>();
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Hearts", i));
		}
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Spades", i));
		}
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Clubs", i));
		}
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Diamonds", i));
		}
	}
	
	//this method returns a hand of two cards
	public ArrayList<Card> dealHand() {
		//if the deck empty somehow, refresh the deck
		if(deck.isEmpty()) {
			generateDeck();
			shuffleDeck();
		}
		//create a new arraylist
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(deck.remove(0));
		//if the deck empty somehow now, refresh the deck
		if(deck.isEmpty()) {
			generateDeck();
			shuffleDeck();
		}
		hand.add(deck.remove(0));
		//return our hand
		return hand;
	}
	
	//just return one card fromm the deck
	public Card drawOne() {
		//if the deck empty somehow, refresh the deck
		if(deck.isEmpty()) {
			generateDeck();
			shuffleDeck();
		}
		//return the card, while removing it from the deck
		return deck.remove(0);
	}
	
	//shuffle the deck, simply use the method in Collections
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	//deck is an array list
	//so this method simply calls on the size property of the deck
	//to return the deck size
	public int deckSize() {
		return deck.size();
	}
	
	//this is our dealer constructor
	BaccaratDealer() {
		//by default it generates the deck,
		//then shuffles it
		generateDeck();
		shuffleDeck();
	}
}
