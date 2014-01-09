/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peerlibraryrmitresette;

import commonclasslibrary.Card;
import commonclasslibrary.Card.Suit;

/**
 *
 * @author spi
 */
public abstract class GameRules {
    private static String botMessage = "";

    private static Double[] points = new Double[]{0.34, 0.34, 1.0, 0.34, 0.34, 0.34, 0.0, 0.0, 0.0, 0.0};

    public static int getHandWinner(Card[] cardslist, int firstplayer) {
        int winner = firstplayer;
        System.out.println("The firstplayer is: " + firstplayer);
        Suit handsuit = cardslist[firstplayer].getSuit();
        for (int i = 0; i < 4; i++) {
            if ((cardslist[i].getSuit() == handsuit) && (cardslist[i].getRank().compareTo(cardslist[winner].getRank()) < 0)) {
                winner = i;
            }
        }
        return winner;
    }

    public static int[] viewBotCards(Card[] cardList) {     //cerca di capire quali giochi ha a disposizione
        botMessage = "";
        int[] power = new int[4];           //bastoni,coppe,denari,spade
        for (int i = 0; i < power.length; i++) {
            power[i] = 0;
        }

        for (Card c : cardList) {
            if ((c.getRank().equals(Card.Rank.THREE))) {
                if (c.getSuit().equals(Card.Suit.BASTONI)) {
                    power[0] = power[0] + 4;
                } else if (c.getSuit().equals(Card.Suit.COPPE)) {
                    power[1] = power[1] + 4;
                } else if (c.getSuit().equals(Card.Suit.DENARI)) {
                    power[2] = power[2] + 4;
                } else if (c.getSuit().equals(Card.Suit.SPADE)){
                    power[3] = power[3] + 4;
                }
            }
            if ((c.getRank().equals(Card.Rank.TWO))) {
                if (c.getSuit().equals(Card.Suit.BASTONI)) {
                    power[0] = power[0] + 2;
                } else if (c.getSuit().equals(Card.Suit.COPPE)) {
                    power[1] = power[1] + 2;
                } else if (c.getSuit().equals(Card.Suit.DENARI)) {
                    power[2] = power[2] + 2;
                } else if (c.getSuit().equals(Card.Suit.SPADE)){
                    power[3] = power[3] + 2;
                }
            }
            if ((c.getRank().equals(Card.Rank.ACE))) {
                if (c.getSuit().equals(Card.Suit.BASTONI)) {
                    power[0] = power[0] + 1;
                } else if (c.getSuit().equals(Card.Suit.COPPE)) {
                    power[1] = power[1] + 1;
                } else if (c.getSuit().equals(Card.Suit.DENARI)) {
                    power[2] = power[2] + 1;
                } else if (c.getSuit().equals(Card.Suit.SPADE)){
                    power[3] = power[3] + 1;
                }
            }
        }
        for (int i = 0; i < 4; i++)
            System.out.println("The POWER of " + i + " is: " + power[i]);
        return power;
    }

    public static Card botSelectionCard(Card[] cardList, Card[] playedCard, Card[] onTable) {   //il bot deve rispondere ad una giocata
        Card carta = null;
        Card first = onTable[0];
        int[] power = new int[4];
        power = viewBotCards(cardList);
        int gioco = -1;

        switch (first.getSuit()) {        //seme della prima carta giocata
            case BASTONI:
                gioco = 0;
                break;
            case COPPE:
                gioco = 1;
                break;
            case DENARI:
                gioco = 2;
                break;
            default:
                gioco = 3;
                break;
        }

        if (power[gioco] == 0) {

            for (int i = 0; i < cardList.length; i++) {     //scarto una carta di quel seme, se presente
                if (cardList[i].getSuit().equals(first.getSuit())) {
                    carta = new Card(cardList[i].getSuit(), cardList[i].getRank());
                    return carta;
                }
            }
            //non ho carte di quel seme, il bot deve scartare
            for (int i = 0; i < onTable.length; i++) {  //controllo se c'è un 3 a tavola, se sì controllo se è del compagno del bot
                if (onTable[i].getRank().equals(Card.Rank.THREE)) {
                    if (onTable[i].getSuit().equals(first.getSuit())) {
                        if (((onTable.length == 3) && (i == 1)) || ((onTable.length == 2) && (i == 0))) {  //il 3 l'ha giocato il compagno
                            for (int j = 0; j < cardList.length; j++) {
                                if (cardList[j].getRank().equals(Card.Rank.ACE)) {
                                    carta = new Card(cardList[j].getSuit(), cardList[j].getRank());
                                    return carta;
                                }
                            }
                        }
                    }
                }
            }
            boolean three = false;
            for (int i = 0; i < playedCard.length; i++) {
                if (playedCard[i].getSuit().equals(first.getSuit())) {
                    if (playedCard[i].getRank().equals(Card.Rank.THREE)) {
                        three = true;
                    }
                }
            }
            for (int i = 0; i < onTable.length; i++) {  //controllo se un 2 è a tavola, se sì controllo se il 3 è uscito, se sì se è del compagno del bot
                if (onTable[i].getRank().equals(Card.Rank.TWO)) {
                    if (onTable[i].getSuit().equals(first.getSuit())) {
                        if (three) {
                            if (((onTable.length == 3) && (i == 1)) || ((onTable.length == 2) && (i == 0))) {  //il 2 l'ha giocato il compagno
                                for (int j = 0; j < cardList.length; j++) {
                                    if (cardList[j].getRank().equals(Card.Rank.ACE)) {
                                        carta = new Card(cardList[j].getSuit(), cardList[j].getRank());
                                        return carta;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < power.length; i++) {        //controllo se il bot deficita in qualche altro gioco
                if ((power[i] == 0) && (i != gioco)) {
                    for (int j = 0; j < cardList.length; j++) {
                        if (cardList[j].getSuit().equals(first.getSuit())) {
                            carta = new Card(cardList[j].getSuit(), cardList[j].getRank());
                            return carta;
                        }
                    }
                }
            }
            //devo scartare nei semi dove ho qualcosa
            for(int i=0;i<power.length;i++){        //scarto dove ho il 3
                if(power[i] == 4){
                    for(int j=0;j<cardList.length;j++){
                        if(cardList[j].getSuit().equals(first.getSuit()))
                            if(!(cardList[j].getRank().equals(Card.Rank.THREE))){
                                carta = new Card(cardList[j].getSuit(), cardList[j].getRank());
                                return carta;
                            }
                    }
                }
            }
            for(int i=0;i<power.length;i++){        //scarto dove ho l'asso
                if(power[i] == 1){
                    for(int j=0;j<cardList.length;j++){
                        if(cardList[j].getSuit().equals(first.getSuit()))
                            if(!(cardList[j].getRank().equals(Card.Rank.ACE))){
                                carta = new Card(cardList[j].getSuit(), cardList[j].getRank());
                                return carta;
                            }
                    }
                }
            }
            for(int i=0;i<power.length;i++){        //scarto dove ho il 2
                if(power[i] == 2){
                    for(int j=0;j<cardList.length;j++){
                        if(cardList[j].getSuit().equals(first.getSuit()))
                            if(!(cardList[j].getRank().equals(Card.Rank.TWO))){
                                carta = new Card(cardList[j].getSuit(), cardList[j].getRank());
                                return carta;
                            }
                    }
                }
            }
            
            //se arriva fino a qui, il bot ha solo carte da tresette
            for(int i=0;i<cardList.length;i++)      //scarto un 2, ormai secco
                if(cardList[i].getRank().equals(Card.Rank.TWO)){
                    carta = new Card(cardList[i].getSuit(), Card.Rank.TWO);
                    return carta;
                }
            for(int i=0;i<cardList.length;i++)      //scarto un asso, ormai secco
                if(cardList[i].getRank().equals(Card.Rank.ACE)){
                    carta = new Card(cardList[i].getSuit(), Card.Rank.ACE);
                    return carta;
                }
            for(int i=0;i<cardList.length;i++)      //ho solo 3, ne scarto uno
                if(cardList[i].getRank().equals(Card.Rank.THREE)){
                    carta = new Card(cardList[i].getSuit(), Card.Rank.THREE);
                    return carta;
                }
        } else {                                                                  //IL BOT POSSIEDE QUALCOSA IN QUEL GIOCO
            if (power[gioco] == 1) {                                              //HA L'ASSO DI QUEL SEME
                int countCard = 0;
                for (int i = 0; i < cardList.length; i++) {
                    if (cardList[i].getSuit().equals(first.getSuit())) {
                        countCard++;
                    }
                }
                if (countCard == 1) {         //se è secco lo gioco
                    carta = new Card(first.getSuit(), Card.Rank.ACE);
                    return carta;
                }

                boolean three = false;
                boolean two = false;
                for (int i = 0; i < playedCard.length; i++) {
                    if (playedCard[i].getRank().equals(Card.Rank.THREE)) {
                        if (playedCard[i].getSuit().equals(first.getSuit())) {
                            three = true;
                        }
                    }
                    if (playedCard[i].getRank().equals(Card.Rank.TWO)) {
                        if (playedCard[i].getSuit().equals(first.getSuit())) {
                            two = true;
                        }
                    }
                }
                if (three && two) {           //3 e 2 giocati
                    carta = new Card(first.getSuit(), Card.Rank.ACE);
                    return carta;
                }

                for (int i = 0; i < onTable.length; i++) {  //controllo se il 3 è a tavola, se sì controllo se è del compagno del bot
                    if (onTable[i].getRank().equals(Card.Rank.THREE)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            if (((onTable.length == 3) && (i == 1)) || ((onTable.length == 2) && (i == 0))) {  //il 3 l'ha giocato il compagno
                                carta = new Card(first.getSuit(), Card.Rank.ACE);
                                return carta;
                            } else {
                                for (int j = 0; j < cardList.length; j++) {
                                    if (cardList[j].getSuit().equals(first.getSuit())) {
                                        if (!(cardList[j].getRank().equals(Card.Rank.ACE))) {
                                            carta = new Card(first.getSuit(), cardList[j].getRank());
                                            return carta;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i = 0; i < onTable.length; i++) {  //controllo se il 2 è a tavola, se sì controllo se il 3 è uscito e se è del compagno del bot
                    if (onTable[i].getRank().equals(Card.Rank.TWO)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            if (three) {
                                if (((onTable.length == 3) && (i == 1)) || ((onTable.length == 2) && (i == 0))) {  //il 2 l'ha giocato il compagno
                                    carta = new Card(first.getSuit(), Card.Rank.ACE);
                                    return carta;
                                } else {
                                    for (int j = 0; j < cardList.length; j++) {
                                        if (cardList[j].getSuit().equals(first.getSuit())) {
                                            if (!(cardList[j].getRank().equals(Card.Rank.ACE))) {
                                                carta = new Card(first.getSuit(), cardList[j].getRank());
                                                return carta;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                if(countCard == 2){     //asso secondo, non ci sono 3 e 2 a tavola, lo gioco
                    boolean tre = false;
                    boolean due = false;
                    for(int i=0;i<onTable.length;i++){
                        if( (onTable[i].getRank().equals(Card.Rank.THREE)) && onTable[i].getSuit().equals(first.getSuit()) )
                            tre = true;
                        if( (onTable[i].getRank().equals(Card.Rank.TWO)) && onTable[i].getSuit().equals(first.getSuit()) )
                            due = true;
                    }
                    if (!tre && !due){
                        carta = new Card(first.getSuit(), Card.Rank.ACE);
                        return carta;
                    }
                    
                }
                                

                if (countCard > 2) {     // asso coperto, gioco una scartina
                    for (int i = 0; i < cardList.length; i++) {
                        if (cardList[i].getSuit().equals(first.getSuit())) {
                            if (!(cardList[i].getRank().equals(Card.Rank.ACE))) {
                                carta = new Card(first.getSuit(), cardList[i].getRank());
                                return carta;
                            }
                        }
                    }
                }
                //inutile, ma non si sa mai
                carta = new Card(first.getSuit(), Card.Rank.ACE);
                return carta;



            } else if (power[gioco] == 2) {                                        //HA IL 2
                int countCard = 0;
                for (int i = 0; i < cardList.length; i++) {
                    if (cardList[i].getSuit().equals(first.getSuit())) {
                        countCard++;
                    }
                }
                if(countCard == 1){     //2 secco
                    carta = new Card(first.getSuit(), Card.Rank.TWO);
                    return carta;
                }
                boolean three = false;
                boolean ace = false;
                for (int i = 0; i < onTable.length; i++) {
                    if (onTable[i].getRank().equals(Card.Rank.ACE)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            ace = true;
                        }
                    }
                    if (onTable[i].getRank().equals(Card.Rank.THREE)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            three = true;
                        }
                    }
                }
                if (ace && !three) {                 //asso a tavola, 3 ancora non giocato: gioco il 2
                    carta = new Card(first.getSuit(), Card.Rank.TWO);
                    return carta;
                }
                if (three) {             // c'è il 3 a tavola, cerco di non scartare il 2
                    for (int i = 0; i < cardList.length; i++) {
                        if ((cardList[i].getSuit().equals(first.getSuit())) && (!(cardList[i].getRank().equals(Card.Rank.TWO)))) {
                            carta = new Card(cardList[i].getSuit(), cardList[i].getRank());
                            return carta;
                        }
                    }
                    // ho solo 2 di quel seme, sono costretto a giocarlo
                    carta = new Card(first.getSuit(), Card.Rank.TWO);
                    return carta;
                }
                for (int i = 0; i < playedCard.length; i++) //il 3 è gia uscito, gioco il 2
                {
                    if (playedCard[i].getSuit().equals(first.getSuit())) {
                        if (playedCard[i].getRank().equals(Card.Rank.THREE)) {
                            carta = new Card(first.getSuit(), Card.Rank.TWO);
                            return carta;
                        }
                    }
                }
                
                if (countCard == 2) {        //2 secondo, lo gioco
                    carta = new Card(first.getSuit(),Card.Rank.TWO);
                    return carta;
                } else {
                    for (int i = 0; i < cardList.length; i++) {
                        if (cardList[i].getSuit().equals(first.getSuit())) {
                            if (!(cardList[i].getRank().equals(Card.Rank.TWO))) {
                                carta = new Card(first.getSuit(), cardList[i].getRank());
                                return carta;
                            }
                        }
                    }
                }

            } else if (power[gioco] == 4) {                                       //HA IL 3
                boolean ace = false;
                for (int i = 0; i < onTable.length; i++) // se c'è asso a tavola
                {
                    if (onTable[i].getRank().equals(Card.Rank.ACE)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            ace = true;
                        }
                    }
                }
                if (ace) {
                    carta = new Card(first.getSuit(), Card.Rank.THREE);
                    return carta;
                }
                boolean two = false;
                for (int i = 0; i < playedCard.length; i++) // se il 2 è stato giocato, gioco il 3
                {
                    if (playedCard[i].getRank().equals(Card.Rank.TWO)) {
                        if (playedCard[i].getSuit().equals(first.getSuit())) {
                            carta = new Card(first.getSuit(), Card.Rank.THREE);
                            return carta;
                        }
                    }
                }
                int countCard = 0;
                for (int i = 0; i < cardList.length; i++) {
                    if (cardList[i].getSuit().equals(first.getSuit())) {
                        countCard++;
                    }
                }
                if (countCard == 1) {        //3 secco, lo gioco
                    carta = new Card(first.getSuit(), Card.Rank.THREE);
                    return carta;
                } else {                      //altrimenti lo tengo
                    for (int i = 0; i < cardList.length; i++) {
                        if (cardList[i].getSuit().equals(first.getSuit())) {
                            if (!(cardList[i].getRank().equals(Card.Rank.THREE))) {
                                carta = new Card(first.getSuit(), cardList[i].getRank());
                                return carta;
                            }
                        }
                    }
                }
            } else if (power[gioco] == 3) {                                        //HA 2 E ASSO
                for (int i = 0; i < playedCard.length; i++) // il 3 è uscito, gioco l'asso
                {
                    if (playedCard[i].getRank().equals(Card.Rank.THREE)) {
                        if (playedCard[i].getSuit().equals(first.getSuit())) {
                            carta = new Card(first.getSuit(), Card.Rank.ACE);
                            return carta;
                        }
                    }
                }
                int countCard = 0;
                for (int i = 0; i < cardList.length; i++) {
                    if (cardList[i].getSuit().equals(first.getSuit())) {
                        countCard++;
                    }
                }
                boolean three = false;
                for (int i = 0; i < onTable.length; i++) // il 3 è a tavola
                {
                    if (onTable[i].getRank().equals(Card.Rank.THREE)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            if (countCard == 2) {                     // se ho solo 2 carte di quel seme scarto il 2
                                carta = new Card(first.getSuit(), Card.Rank.TWO);
                                return carta;
                            } else {                                  //altrimenti scarto un'altra carta di quel seme
                                for (int j = 0; j < cardList.length; j++) {
                                    if (cardList[j].getSuit().equals(first.getSuit())) {
                                        if (!(cardList[j].getRank().equals(Card.Rank.TWO))) {
                                            if (!(cardList[j].getRank().equals(Card.Rank.ACE))) {
                                                carta = new Card(first.getSuit(), cardList[j].getRank());
                                                return carta;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (countCard == 2) {        //ho solo asso e due, gioco l'asso
                    carta = new Card(first.getSuit(), Card.Rank.ACE);
                    return carta;
                }
                for (int j = 0; j < cardList.length; j++) //altrimenti gioco il 2
                {
                    if (cardList[j].getSuit().equals(first.getSuit())) {
                        if (cardList[j].getRank().equals(Card.Rank.TWO)){
                            carta = new Card(first.getSuit(), cardList[j].getRank());
                                return carta;
                            }
                    }
                }
            } else if (power[gioco] == 5) {                                        //HA 3 E ASSO
                for (int i = 0; i < playedCard.length; i++) // il 2 è uscito, gioco l'asso
                {
                    if (playedCard[i].getRank().equals(Card.Rank.TWO)) {
                        if (playedCard[i].getSuit().equals(first.getSuit())) {
                            carta = new Card(first.getSuit(), Card.Rank.ACE);
                            return carta;
                        }
                    }
                }
                int countCard = 0;
                for (int i = 0; i < cardList.length; i++) {
                    if (cardList[i].getSuit().equals(first.getSuit())) {
                        countCard++;
                    }
                }
                for (int i = 0; i < onTable.length; i++) // il 2 è a tavola, lo mangio
                {
                    if (onTable[i].getRank().equals(Card.Rank.TWO)) {
                        if (onTable[i].getSuit().equals(first.getSuit())) {
                            carta = new Card(first.getSuit(), Card.Rank.THREE);
                            return carta;
                        }
                    }
                }
                if (countCard > 2) {
                    carta = new Card(first.getSuit(), Card.Rank.THREE);
                    return carta;
                }
                if (countCard == 2) {        //ho solo asso e 3, gioco l'asso
                    carta = new Card(first.getSuit(), Card.Rank.ACE);
                    return carta;
                }
            } else if (power[gioco] == 6) {                                       //HA 3 E 2
                carta = new Card(first.getSuit(), Card.Rank.TWO);
                return carta;
            } else if (power[gioco] == 7) {                                        //CRICCA
                carta = new Card(first.getSuit(), Card.Rank.ACE);
                return carta;
            }
        }

        return carta;
    }

    public static Card botInitSelectionCard(Card[] cardList, Card[] playedCard) {   //il bot gioca per primo
        Card carta = null;
        int[] power = new int[4]; //bastoni,coppe,denari,spade
        for (int p : power) {
            p = 0;
        }

        power = viewBotCards(cardList);

        for (int i = 0; i < power.length; i++) {       //cricca
            if (power[i] == 7) {
                switch (i) {
                    case 0:
                        carta = new Card(Suit.BASTONI, Card.Rank.ACE);
                        botMessage = "Cricca bastoni";
                        break;
                    case 1:
                        carta = new Card(Suit.COPPE, Card.Rank.ACE);
                        botMessage = "Cricca coppe";
                        break;
                    case 2:
                        carta = new Card(Suit.DENARI, Card.Rank.ACE);
                        botMessage = "Cricca denari";
                        break;
                    default:
                        carta = new Card(Suit.SPADE, Card.Rank.ACE);
                        botMessage = "Cricca spade";
                        break;
                }
                return carta;
            }
        }


        for (int i = 0; i < power.length; i++) {     //3 e 2 di uno stesso seme
            if (power[i] == 6) {
                botMessage = "Cerco asso";
                switch (i) {
                    case 0:
                        carta = new Card(Suit.BASTONI, Card.Rank.THREE);
                        break;
                    case 1:
                        carta = new Card(Suit.COPPE, Card.Rank.THREE);
                        break;
                    case 2:
                        carta = new Card(Suit.DENARI, Card.Rank.THREE);
                        break;
                    default:
                        carta = new Card(Suit.SPADE, Card.Rank.THREE);
                        break;
                }
                return carta;
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 2) {                  //ho il 2
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }

                for (int j = 0; j < playedCard.length; j++) {
                    if (playedCard[j].getSuit().equals(temp.getSuit())) {
                        if ((playedCard[j].getRank().equals(Card.Rank.THREE))) {   //il 3 dello stesso seme è stato giocato
                            carta = new Card(temp.getSuit(), Card.Rank.TWO);
                            return carta;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 0) {                  //caso in cui non ho niente, ma è tutto fuori
                boolean control3 = false;
                boolean control2 = false;
                boolean controlA = false;
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }

                for (int j = 0; j < playedCard.length; j++) {
                    if (playedCard[j].getSuit().equals(temp.getSuit())) {
                        if ((playedCard[j].getRank().equals(Card.Rank.TWO))) //il 2 dello stesso seme è stato giocato
                        {
                            control2 = true;
                        } else if ((playedCard[j].getRank().equals(Card.Rank.THREE))) //il 3 dello stesso seme è stato giocato
                        {
                            control3 = true;
                        } else if ((playedCard[j].getRank().equals(Card.Rank.ACE))) //l'asso dello stesso seme è stato giocato
                        {
                            controlA = true;
                        }
                        if (control2 && control3 && controlA) {     //gioco qualsiasi carta di quel seme
                            botMessage = "Tutto fuori";
                            for (int k = 0; k < cardList.length; k++) {     //se è fuori tutto in quel gioco, probabilmente solo il bot lo possiede
                                if (cardList[k].getSuit().equals(temp.getSuit())) {
                                    carta = new Card(temp.getSuit(), cardList[k].getRank());
                                    return carta;
                                }
                            }
                        }
                    }
                }
            }
        }


        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 5) {                  //3 e ace di un seme
                switch (i) {
                    case 0:
                        carta = new Card(Suit.BASTONI, Card.Rank.THREE);
                        break;
                    case 1:
                        carta = new Card(Suit.COPPE, Card.Rank.THREE);
                        break;
                    case 2:
                        carta = new Card(Suit.DENARI, Card.Rank.THREE);
                        break;
                    default:
                        carta = new Card(Suit.SPADE, Card.Rank.THREE);
                        break;
                }
                botMessage = "Cerco il 2";
                return carta;
            }
        }

        for (int i = 0; i < power.length; i++) {
            if (power[i] == 3) {                  //2 e ace di un seme
                switch (i) {
                    case 0:
                        carta = new Card(Suit.BASTONI, Card.Rank.TWO);
                        break;
                    case 1:
                        carta = new Card(Suit.COPPE, Card.Rank.TWO);
                        break;
                    case 2:
                        carta = new Card(Suit.DENARI, Card.Rank.TWO);
                        break;
                    default:
                        carta = new Card(Suit.SPADE, Card.Rank.TWO);
                        break;
                }
                botMessage = "Cerco il 3";
                return carta;
            }
        }

        //non ci sono giochi importanti in mano, ne cerco uno basandomi sulle carte già giocate
        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 4) {                  //ho il 3
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }

                for (int j = 0; j < playedCard.length; j++) {
                    if (playedCard[j].getSuit().equals(temp.getSuit())) {
                        if ((playedCard[j].getRank().equals(Card.Rank.TWO))) {   //il 2 dello stesso seme è stato giocato
                            carta = new Card(temp.getSuit(), Card.Rank.THREE);
                            return carta;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 1) {                  //ho l'asso
                boolean control3 = false;
                boolean control2 = false;
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }

                for (int j = 0; j < playedCard.length; j++) {
                    if (playedCard[j].getSuit().equals(temp.getSuit())) {
                        if ((playedCard[j].getRank().equals(Card.Rank.TWO))) //il 2 dello stesso seme è stato giocato
                        {
                            control2 = true;
                        } else if ((playedCard[j].getRank().equals(Card.Rank.THREE))) //il 3 dello stesso seme è stato giocato
                        {
                            control3 = true;
                        }
                        if (control2 && control3) {
                            botMessage = "Buona";
                            carta = new Card(temp.getSuit(), Card.Rank.ACE);    //gioco l'asso
                            return carta;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 4) {                  //ho il 3, ma il due non è ancora uscito
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }
                for (int j = 0; j < cardList.length; j++) {        //cerco un'altra carta dello stesso seme, altrimenti abbandono questo gioco
                    if (cardList[j].getSuit().equals(temp.getSuit())) {
                        if (cardList[j].getRank().equals(Card.Rank.THREE)) {
                            continue;
                        } else {
                            carta = new Card(temp.getSuit(), cardList[j].getRank());
                            botMessage = "Voglio il 2";
                            return carta;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 2) {                  //ho il 2, ma il tre non è ancora uscito
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }
                boolean altra = false;
                for (int j = 0; j < cardList.length; j++) {
                    if (cardList[j].getSuit().equals(temp.getSuit() )) {     //controllo se ho altre due carte dello stesso seme
                        if (cardList[j].getRank().equals(Card.Rank.TWO)) {
                            continue;
                        } else {
                            if (altra) {
                                carta = new Card(temp.getSuit(), cardList[j].getRank());
                                botMessage = "Voglio il 3";
                                return carta;
                            }
                            altra = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 1) {                  //ho asso, ma ne tre ne 2 sono ancora usciti
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }
                boolean altra1 = false;
                boolean altra2 = false;
                for (int j = 0; j < cardList.length; j++) {     //controllo se l'asso è coperto (altre 3 carte dello stesso seme)
                    if (cardList[j].getSuit().equals(temp.getSuit()) ) {
                        if (cardList[j].getRank().equals(Card.Rank.ACE)) {
                            continue;
                        } else {
                            if (altra1 && altra2) {
                                botMessage = "Ho asso";
                                carta = new Card(temp.getSuit(), cardList[j].getRank());
                                return carta;
                            }
                            if (altra1) {
                                altra2 = true;
                            } else {
                                altra1 = true;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 4) {                  //ho 3 secco, lo gioco
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }
                for (int j = 0; j < cardList.length; j++) {
                    if (cardList[j].getSuit().equals(temp.getSuit())) {
                        if (cardList[j].getRank().equals(Card.Rank.THREE)) {
                            carta = new Card(temp.getSuit(), Card.Rank.THREE);
                            return carta;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < power.length; i++) {
            Card temp = null;
            if (power[i] == 2) {                  //ho 2 secco, lo gioco
                switch (i) {
                    case 0:
                        temp = new Card(Suit.BASTONI, Card.Rank.ACE);
                        break;
                    case 1:
                        temp = new Card(Suit.COPPE, Card.Rank.ACE);
                        break;
                    case 2:
                        temp = new Card(Suit.DENARI, Card.Rank.ACE);
                        break;
                    default:
                        temp = new Card(Suit.SPADE, Card.Rank.ACE);
                        break;
                }
                for (int j = 0; j < cardList.length; j++) {
                    if (cardList[j].getSuit().equals(temp.getSuit())) {
                        if (cardList[j].getRank().equals(Card.Rank.TWO)) {
                            carta = new Card(temp.getSuit(), Card.Rank.TWO);
                            return carta;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < cardList.length; i++) {            //gioco qualunque carta tranne un asso
            if (cardList[i].getRank().equals(Card.Rank.ACE)) {
                continue;
            } else {
                carta = new Card(cardList[i].getSuit(), cardList[i].getRank());
                return carta;
            }
        }
        
        //se nn cado in nessuna delle casistiche precedenti, gioco la prima carta a caso
        carta = new Card(cardList[0].getSuit(), cardList[0].getRank());
        return carta;
    }

    public static Double getHandPoints(Card[] cardslist) {
        Double p = 0.0;
        for (int i = 0; i < 4; i++) {
            p += points[cardslist[i].getRank().ordinal()];
        }
        return p;
    }

    public static boolean isItAPlayableCard(Card card, Card firstcardofthehand, Card[] theothercards) {
        //System.out.println("The card is of: " + card.getSuit().name() +
        //        " and the first player played a card of: " + firstcardofthehand.getSuit().name());
        if (card.getSuit() == firstcardofthehand.getSuit()) {
            return true;
        } else {
            int i;
            for (i = 0; i < theothercards.length; i++) {
                if ((theothercards[i] != null) && (theothercards[i].getSuit() == firstcardofthehand.getSuit())) {
                    break;
                }
            }
            //System.out.println("The i of the suit checking is: " + i + " and the length is: " + theothercards.length);
            if (i < theothercards.length) {
                System.out.println("It is not a playable card because the " + i + " card is the same suit as the first card played");
                return false;
            } else {
                return true;
            }
        }
    }
    
    public static String getBotMessage(){
        return botMessage;
    }
}
