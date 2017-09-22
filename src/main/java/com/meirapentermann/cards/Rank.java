package com.meirapentermann.cards;

public enum Rank {
	  ACE("Ace of ", "A")
	, TWO("Two of ", "2")
	, THREE("Three of ", "3")
	, FOUR("Four of ", "4")
	, FIVE("Five of ", "5")
	, SIX("Six of ", "6")
	, SEVEN("Seven of ", "7")
	, EIGHT("Eight of ", "8")
	, NINE("Nine of ", "9")
	, TEN("Ten of ", "10")
	, JACK("Jack of ", "J")
	, QUEEN("Queen of ", "Q")
	, KING("King of ", "K");
	
	public String displayText;
	public String displayShort;
	
	/*
	 * Constructor forces full set-up
	 */
	private Rank (String s, String c) {
		this.displayText = s;
		this.displayShort = c;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public String getDisplayShort() {
		return displayShort;
	}

	public void setDisplayShort(String displayShort) {
		this.displayShort = displayShort;
	}

}
