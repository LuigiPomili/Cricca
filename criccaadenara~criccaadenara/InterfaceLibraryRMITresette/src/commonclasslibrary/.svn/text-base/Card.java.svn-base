/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commonclasslibrary;

import java.io.Serializable;

/**
 *
 * @author spi
 */
public class Card implements Serializable {
    
    public enum Suit {

        SPADE, DENARI, BASTONI, COPPE
    }
    
    public enum Rank {

        THREE, TWO, ACE, RE, CAVALLO, FANTE, SEVEN, SIX, FIVE, FOUR
    }

    protected Suit suit;
    protected Rank rank;

    public Rank getRank() {
        return rank;
    }

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    
    @Override
    public boolean equals(Object obj){
        Card card = (Card)obj;
        if ((card.getRank() == this.getRank()) && (card.getSuit() == this.getSuit()))
            return true;
        else
            return false;
    }
}
