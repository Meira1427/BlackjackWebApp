package com.meirapentermann.participant;

import java.util.List;

import com.meirapentermann.blackjack.Hand;
import com.meirapentermann.cards.Card;

public abstract class Participant {
	private String name;
	private Hand hand;
	private Hand split;

	/*
	 * need a way to reach in and add a card
	 */
	public void updateHand(Card c) {
		this.hand.addCard(c);
	}
	
	public void updateHandSplit(Card c) {
		this.split.addCard(c);
	}
	
	/*
	 * Getters & Setters
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getHandList() { // Note: returning this in List<Card> format, so we can choose
		return hand.getHandList();	  //   to loop through the List of Cards. More versatile
	}
	
	public Hand getHandHand () { // Need this one for the hand only methods
		return hand;
	}
	
	public Card getPartialHand() { //for displaying only one card of dealer
		return hand.getHandList().get(1);
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public int handValue() {			// for easier grab at Player & Dealer level
		return this.hand.handValue();
	}
	
	/*
	 * 2nd hand as a split
	 */

	public List<Card> getSplitList() {
		return split.getHandList();
	}
	
	public Hand getSplitHand() {
		return split;
	}

	public void setSplit(Hand split) {
		this.split = split;
	}
	
	public int splitHandValue() {
		return this.split.handValue();
	}
	
	

}
