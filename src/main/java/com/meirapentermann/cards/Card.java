package com.meirapentermann.cards;

public class Card {
	private Suit suit;
	private Rank rank;
	private int value;
	
//	public Card() {
//	}
	
	public Card(Suit suit, Rank rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/*
	 * Format produces cards like this:
	 * [ ♡ 10 ♡  Ten of Hearts  ♡ 10 ♡ ]
	 * [ ♧ K ♧  King of Clubs  ♧ K ♧ ]
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("[ ");
		sb.append(suit.displayChar);
		sb.append(" ");
		sb.append(rank.displayShort);
		sb.append(" ");
		sb.append(suit.displayChar);
		sb.append("  ");
		sb.append(rank.displayText);
		sb.append(suit.displayText);
		sb.append("  ");
		sb.append(suit.displayChar);
		sb.append(" ");
		sb.append(rank.displayShort);
		sb.append(" ");
		sb.append(suit.displayChar);
		sb.append(" ]\n");
		return sb.toString();
	}

}
