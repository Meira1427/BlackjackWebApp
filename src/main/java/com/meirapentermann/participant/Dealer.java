package com.meirapentermann.participant;

import java.util.List;

import com.meirapentermann.blackjack.Hand;
import com.meirapentermann.cards.Card;

public class Dealer extends Participant {
	
	public Dealer () {
		this.setName("Dealer");
		Hand hand = new Hand(); //initialize a hand
		this.setHand(hand);
	}
}
