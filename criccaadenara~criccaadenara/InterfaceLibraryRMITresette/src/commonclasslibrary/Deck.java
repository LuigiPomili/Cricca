/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commonclasslibrary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lele
 */
public class Deck implements Serializable{
    Card[][] deck = new Card[4][10];

    public Card[][] getDeck() {
        return deck;
    }
    
    public Deck(){
        deck = new Card[4][10];
        for (Card.Suit seme : Card.Suit.values())
            for(Card.Rank val : Card.Rank.values())
                deck[seme.ordinal()][val.ordinal()] = new Card(seme,val);
    }
    
    public Card[][] getCardRandomCardList(){
        List<Card> cardslist = new ArrayList<Card>();
        for (Card.Suit suit : Card.Suit.values())
            for (Card.Rank rank : Card.Rank.values())
                cardslist.add(new Card(suit, rank));
        Random rnd = new Random();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 10; j ++)
                deck[i][j] = cardslist.remove(rnd.nextInt(cardslist.size()));
        return deck;
    }
    
    public int[] whatIsTheAddressOfThisCard(Card card){
        for(int i=0;i<deck.length;i++)
            for(int j=0;j<deck[0].length;j++)
                if ((deck[i][j] != null) && (deck[i][j].equals(card)))
                    return new int[]{i,j};
        return new int[]{-1,-1};
    }
    
    public int[] whatIsTheAddressOfThisCard(Card card, int mynumber){
        for(int i=0;i<deck[mynumber].length;i++)
            if((deck[mynumber][i] != null) && (deck[mynumber][i].equals(card)))
                return new int[]{mynumber,i};
        return new int[]{-1, -1};
    }
    
    public boolean removeACard(Card card, int mynumber){
        int[] cardaddress = whatIsTheAddressOfThisCard(card, mynumber);
        if (cardaddress[0] == -1)
            return false;
        else{
            deck[cardaddress[0]][cardaddress[1]] = null;
            return true;
        }
    }
    
}
