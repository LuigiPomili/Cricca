package peerrmitresettecardgame;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardGame;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jcardgame.RowLayout;
import ch.aplu.jcardgame.StackLayout;
import ch.aplu.jgamegrid.GGButton;
import ch.aplu.jgamegrid.GGButtonListener;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;

import commonclasslibrary.Card.Rank;
import commonclasslibrary.Card.Suit;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox.KeySelectionManager;
import javax.swing.Timer;
import peerlibraryrmitresette.PeerLibraryRMITresette;

/**
 *
 * @author paolo
 */
public class TresetteCardGame extends CardGame implements GGButtonListener {

    private PeerRMITresetteTableList dad;
    private Hand[] hands = null;
    private Hand[] ontablecards = new Hand[4];
    private Hand[] takencards = new Hand[2];
    private PeerLibraryRMITresette peerlib = null;
    private GGButton turn;
    //private TextActor team0score;
    //private TextActor team1score;
    private TresetteKeyListener tkl = new TresetteKeyListener();

    class MyCardValues implements Deck.CardValues {

        @Override
        public int[] values(Enum suit) {
            int[] defaultValues = new int[]{1, 1, 1, 0, 0, 0, 0, 1, 1, 1};
            return defaultValues;
        }
    }
    private Deck deck =
            new Deck(Suit.values(), Rank.values(), "cover", new MyCardValues());

    public TresetteCardGame(PeerRMITresetteTableList dad, final PeerLibraryRMITresette peerlib) {
        super(900, 700, 30);
        setStatusText("Welcome to Tresette Card Game");
        this.dad = dad;
        this.peerlib = peerlib;
        this.peerlib.setPeergame(this);
        hands = deck.dealingOut(4, 10);

        GGButton cmdExit = new GGButton("sprites/usc.gif");
        turn = new GGButton("sprites/turn.gif");
        addActor(cmdExit, new Location(880, 680));
        cmdExit.addButtonListener(this);

        for (int i = 0; i < 4; i++) {
            ontablecards[i] = new Hand(deck);
        }
        for (int i = 0; i < 2; i++) {
            takencards[i] = new Hand(deck);
        }

        RowLayout takencards0Layout = new RowLayout(new Location(800, 90), 0);
        takencards[0].setVerso(true);
        takencards[0].setView(this, takencards0Layout);
        takencards[0].draw();

        RowLayout takencards1Layout = new RowLayout(new Location(110, 610), 0);
        takencards[1].setVerso(true);
        takencards[1].setView(this, takencards1Layout);
        takencards[1].draw();
    }

    @Override
    public void buttonPressed(GGButton ggb) {
    }

    @Override
    public void buttonReleased(GGButton ggb) {
    }

    @Override
    public void buttonClicked(GGButton ggb) {
        if (peerlib != null){
            peerlib.unregisterRMI();
        }
        peerlib = null;
        refresh();
        this.setVisible(false);
        this.dad.setVisible(true);
    }
    
    public void handsLayout(){
        int realnumber = peerlib.getMynumber();
        Color myteamcolor;
        Color otherteamcolor;
        int teamnumber;
        if ((realnumber % 2) == 0){
            myteamcolor = Color.RED;
            otherteamcolor = Color.BLUE;
            teamnumber = 0;
        }else{
            myteamcolor = Color.BLUE;
            otherteamcolor = Color.RED;
            teamnumber = 1;
        }
        TextActor player1 = new TextActor("Team " + teamnumber + " - Player " + realnumber, myteamcolor, getBgColor(), getFont());
        addActor(player1, new Location(400, 680));
        TextActor player2 = new TextActor(true, "Team " + ((teamnumber + 1) % 2) + " - Player " + ((realnumber + 1) % 4), otherteamcolor, getBgColor(), getFont());
        addActor(player2, new Location(870, 380), 270);
        TextActor player3 = new TextActor(true, "Team " + teamnumber + " - Player " + ((realnumber + 2) % 4), myteamcolor, getBgColor(), getFont());
        addActor(player3, new Location(500, 10), 180);
        TextActor player4 = new TextActor(true, "Team " + ((teamnumber + 1) % 2) + " - Player " + ((realnumber + 3) % 4), otherteamcolor, getBgColor(), getFont());
        addActor(player4, new Location(20, 340), 90);
        TextActor team0 = new TextActor("Team 0 cards taken", Color.RED, getBgColor(), getFont());
        addActor(team0, new Location(740, 10));
        TextActor team1 = new TextActor("Team 1 cards taken", Color.BLUE, getBgColor(), getFont());
        addActor(team1, new Location(50, 530));
        /*team0score = new TextActor("Team 0 score is: 0", Color.RED, getBgColor(), getFont());
        addActor(team0score, new Location(740, 170));
        team1score = new TextActor("Team 1 score is: 0", Color.BLUE, getBgColor(), getFont());
        addActor(team1score, new Location(50, 690));*/
        RowLayout player0Layout = new RowLayout(new Location(450, 590), 500);
        hands[realnumber].setView(this, player0Layout);
        hands[realnumber].setTouchEnabled(true);
        hands[realnumber].putOnTopEnabled(true);
        hands[realnumber].draw();
        ontablecards[realnumber].setView(this, new StackLayout(new Location(450, 400)));
        ontablecards[realnumber].draw();
        hands[realnumber].addCardListener(new CardAdapter() {

            @Override
            public void leftDoubleClicked(Card card) {
                if (!peerlib.broadCastCard(new commonclasslibrary.Card(commonclasslibrary.Card.Suit.valueOf(card.getSuit().name()),
                        commonclasslibrary.Card.Rank.valueOf(card.getRank().name())), tkl.getCurrenttext()))
                    System.out.println("FAIL!");
            }
        });
        
        RowLayout player1Layout = new RowLayout(new Location(780, 350), 300);
        player1Layout.setRotationAngle(270);
        realnumber = (realnumber + 1) % 4;
        hands[realnumber].setVerso(false);
        hands[realnumber].setTouchEnabled(false);
        hands[realnumber].setView(this, player1Layout);
        hands[realnumber].draw();
        ontablecards[realnumber].setView(this, new StackLayout(new Location(500, 350)));
        ontablecards[realnumber].draw();
        
        RowLayout player2Layout = new RowLayout(new Location(450, 100), 300);
        player2Layout.setRotationAngle(180);
        realnumber = (realnumber + 1) % 4;
        hands[realnumber].setVerso(false);
        hands[realnumber].setTouchEnabled(false);
        hands[realnumber].setView(this, player2Layout);
        hands[realnumber].draw();
        ontablecards[realnumber].setView(this, new StackLayout(new Location(450, 300)));
        ontablecards[realnumber].draw();
        
        RowLayout player3Layout = new RowLayout(new Location(110, 350), 300);
        player3Layout.setRotationAngle(90);
        realnumber = (realnumber + 1) % 4;
        hands[realnumber].setVerso(false);
        hands[realnumber].setTouchEnabled(false);
        hands[realnumber].setView(this, player3Layout);
        hands[realnumber].draw();
        ontablecards[realnumber].setView(this, new StackLayout(new Location(400, 350)));
        ontablecards[realnumber].draw();
    }

    public void wakeUp() {
            for (int i = 0; i < 4; i++) {
                hands[i] = new Hand(deck);
                for (int j = 0; j < 10; j++) {
                    hands[i].insert(peerlib.getDeck().getDeck()[i][j].getSuit(), peerlib.getDeck().getDeck()[i][j].getRank(), true);
                }
                //hands[i].draw();
                hands[i].sort(Hand.SortType.SUITPRIORITY, false);
            }
            handsLayout();
            peerlib.amIReady(true);
    }

    public void newMove(commonclasslibrary.Card card, int whoplayed, int handwinner) {
        try{
            hands[whoplayed].getCard(card.getSuit(), card.getRank()).transfer(ontablecards[whoplayed], true);
            peerlib.getDeck().removeACard(card, whoplayed);
            if (handwinner != -1) {
                for (int i = 0; i < 4; i++){
                    ontablecards[i].transfer(ontablecards[i].get(0), takencards[(handwinner % 2)], true);
                    /*if ((handwinner % 2) == 0){
                        removeActor(team0score);
                        team0score = new TextActor("Team 0 score is: " + peerlib.getPoints()[0], Color.RED, getBgColor(), getFont());
                        addActor(team0score, new Location(740, 170));
                    }else{
                        removeActor(team1score);
                        team1score = new TextActor("Team 1 score is: " + peerlib.getPoints()[1], Color.BLUE, getBgColor(), getFont());
                        addActor(team1score, new Location(50, 690));
                    }*/
                }
                takencards[(handwinner % 2)].setVerso(true);
                if (hands[0].getNumberOfCards() == 0){
                    TextActor winner;
                    if (peerlib.getPoints()[0].intValue() > peerlib.getPoints()[1].intValue()){
                        /*winner = new TextActor("The winner is Team 0", Color.RED, Color.BLUE, getFont());
                        addActor(winner, new Location(450, 350));*/
                        winnerApplet("Team 0 WIN!!");
                    }else{
                        /*winner = new TextActor("The winner is Team 1", Color.BLUE, Color.RED, getFont());
                        addActor(winner, new Location(450, 350));*/
                        winnerApplet("Team 1 WIN!!");
                    }
                    /*if (peerlib != null){
                        peerlib.unregisterRMI();
                    }*/
                }
            }
        }catch(Exception ex){
            Logger.getLogger(PeerRMITresetteTableList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void wrongMove(Card ontablecard, int index){
        ontablecards[index].transfer(ontablecard, hands[index], true);
    }
    
    public void unexpectedWin(){
        /*TextActor winner;
        if (peerlib.getPoints()[0].intValue() > peerlib.getPoints()[1].intValue()){
            winner = new TextActor("All the player except you had left the game, you win!", Color.RED, Color.BLUE, getFont());
            addActor(winner, new Location(400, 350));
        }else{
            winner = new TextActor("All the player except you had left the game, you win!", Color.BLUE, Color.RED, getFont());
            addActor(winner, new Location(400, 350));
        }*/
        winnerApplet("You win by abandonment!");
        /*if (peerlib != null)
            peerlib.unregisterRMI();
        peerlib = null;*/
    }

    public void winnerApplet(String winner){
        
        BufferedWriter bufferedWriter = null;

        try {
            File conf = new File("applet_testo_fuochiart/applet.htm");
            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter(conf));

            //Start writing to the output stream
            bufferedWriter.write("<APPLET CODE=\"FireWorks.class\" WIDTH=900 HEIGHT=700>");
            bufferedWriter.newLine();
            bufferedWriter.write("<PARAM NAME=\"Text\" VALUE=\" " + winner + "\">");
            bufferedWriter.newLine();
            bufferedWriter.write("</APPLET>");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        try {
            Process process = Runtime.getRuntime().exec("appletviewer applet_testo_fuochiart/applet.htm");
            this.setVisible(false);
            peerlib.unregisterRMI();
            peerlib = null;
            refresh();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TresetteCardGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            process.destroy();
            this.dad.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
    public void remoteSetStatusText(String text){
        setStatusText(text);
    }
    
    public void manageTKL(boolean on){
        if (on){
            if (!getActors().contains(turn))
                addActor(turn, new Location(550,680));
            this.addKeyListener(tkl);
            tkl.setCurrenttext();
        }else {
            this.removeKeyListener(tkl);
            removeActor(turn);
        }    
    }
    
    public class TresetteKeyListener implements KeyListener{
        String currenttext = "";

        public String getCurrenttext() {
            return currenttext;
        }
        
        public void setCurrenttext() {
            currenttext = "";
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() != '\n'){
                if (!currenttext.equals(""))
                    if (e.getKeyChar() != 8)
                        currenttext = currenttext + e.getKeyChar();
                    else
                        currenttext = currenttext.substring(0, currenttext.length() - 1);
                else
                    if (e.getKeyChar() != 8)
                        currenttext = Character.toString(e.getKeyChar());
                    else
                        currenttext = "";
                ((TresetteCardGame)e.getSource()).setStatusText(currenttext);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
        
    }
}