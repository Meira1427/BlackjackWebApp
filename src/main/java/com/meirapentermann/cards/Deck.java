package com.meirapentermann.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deck = new ArrayList<>();
	private int numDealt;
	
	public Deck () {
		this.numDealt = 0;
		this.deck = createDeck();
	}
	
	/*
	 * optional constructor for different values passed in.
	 * must be an array of 13 integers in order of Ace, 2, 3 etc.
	 */
	public Deck (int[] values) {
		this.numDealt = 0;
		this.deck = createDeck(values);
	}
	
	
	/*
	 * Loops through both enums to produce a new deck
	 * chooses default Blackjack values
	 */
	private List<Card> createDeck() {
		List<Card> temp = new ArrayList<>();
		Card c;
		Suit[] suits = {Suit.HEARTS, Suit.SPADES, 
						Suit.CLUBS, Suit.DIAMONDS  };

		Rank[] ranks = {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, 
						Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT,
						Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN,
						Rank.KING };
		int[] values = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
						
		for(int i = 0; i < 13; i++) {
			for(Suit suit : suits) {
				c = new Card(suit, ranks[i], values[i]);
				temp.add(c);
			}
		}
		return temp;
	}
	
	/*
	 * optional createDeck for different values passed in.
	 * must be an array of 13 integers in order of Ace, 2, 3 etc.
	 */
	private List<Card> createDeck(int[] values) {
		List<Card> temp = new ArrayList<>();
		Card c;
		Suit[] suits = {Suit.HEARTS, Suit.SPADES, 
						Suit.CLUBS, Suit.DIAMONDS  };

		Rank[] ranks = {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, 
						Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT,
						Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN,
						Rank.KING };
						
		for(int i = 0; i < 13; i++) {
			for(Suit suit : suits) {
				c = new Card(suit, ranks[i], values[i]);
				temp.add(c);
			}
		}
		return temp;
	}
	
	/*
	 * Shuffles deck
	 */
	public void shuffleDeck() {
		Collections.shuffle(this.deck);
	}
	
	/*
	 * Verify that deck isn't empty
	 * Then deal one card and remove it from deck
	 * if deck is empty, return null
	 */	
	public Card dealCard() {
		if (cardSLeft() > 0) {
			Card c = deck.remove(0);
			this.numDealt++;
			return c;
		}
		else {
			return null;
		}
	}
	
	/*
	 * returns # cards left in deck
	 */
	public int cardSLeft() {
		return this.deck.size();
	}

	/*
	 * Getters and Setters
	 */

	public List<Card> getDeck() {
		return deck;
	}

	public int getNumDealt() {
		return numDealt;
	}

	public void setNumDealt(int numDealt) {
		this.numDealt = numDealt;
	}

	/*
	 * Basic toString() method
	 */
	@Override
	public String toString() {
		return "Deck [" + deck + "]";
	}
	
	
}
