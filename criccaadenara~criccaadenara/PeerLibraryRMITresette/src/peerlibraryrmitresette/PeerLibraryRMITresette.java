/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peerlibraryrmitresette;

import clientlibraryinterfacelibrary.PeerLibraryRMITresetteInterface;
import commonclasslibrary.Card;
import commonclasslibrary.Deck;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverinterfacelibrary.ServerRMITresetteInterface;
import serverinterfacelibrary.TablesListInterface;

/**
 *
 * @author spi
 */
public class PeerLibraryRMITresette<T> extends UnicastRemoteObject implements PeerLibraryRMITresetteInterface{
    
    private ServerRMITresetteInterface srt = null;
    private String serveripandport = null;
    private int registryport = -1;
    private PeerLibraryRMITresette peer = null;
    private PeerLibraryRMITresetteInterface[] peerlist = null;
    private Deck deck = null;
    private int mynumber = -1;
    private boolean token = false;
    private Card[] ontablecards = new Card[4];
    private Double[] points = new Double[]{0.0, 0.0};
    private List<Card> playedcards = new ArrayList<Card>();
    private int firstplayer = -1;
    private T peergame = null;
    private boolean imready = false;
    private int nobots = 0;
    private boolean bottoken = false;
    private int noactivebot = -1;
    private Timer crashtimer;// = new Timer();
    private int crashtimertarget = -1;
    private int whoisplaying = 0;
    private Semaphore sem = new Semaphore(1, true);

    //PeerRMITresette peer = null;

    public PeerLibraryRMITresette(String serveripandport, int registryport, T peergame) throws RemoteException {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
                System.out.println("Security manager installed.");
            } else {
                System.out.println("Security manager already exists.");
            }
            srt = (ServerRMITresetteInterface) Naming.lookup("//" + serveripandport + "/ServerRMITresette");
            //QUESTO CONTROLLO NON FUNGE! C'E' DA RIFARLO!
            /*if (LocateRegistry.getRegistry(registryport) == null) {
                LocateRegistry.createRegistry(registryport);
            }*/
            this.serveripandport = serveripandport;
            this.registryport = registryport;
            this.peer = this;
            this.peergame = peergame;
        } catch (Exception ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createTable(String tablename) {
        try {
            srt.createTable(tablename, registryport);
            mynumber = 0;
            try{
                LocateRegistry.createRegistry(registryport);
                Naming.rebind("//localhost:" + registryport + "/PeerRMITresette0", peer);
            }catch(Exception e){
                System.out.println("e' fallita la creazione del registro, speriamo che sia perche' e' gia' stato creato");
                Naming.rebind("//localhost:" + registryport + "/PeerRMITresette0", peer);
            }
        } catch (Exception ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[][] getTableList(){
        try {
            //In teoria non ci sarebbe da farsi ridare ogni volta la lista
            TablesListInterface tbl = srt.getTableList();
            String[][] stringtablelist = new String[tbl.getTbl().size()][3];
            for(int i = 0; i < tbl.getTbl().size(); i++){
                stringtablelist[i][0] = String.valueOf(tbl.getTable(i).getId());
                stringtablelist[i][1] = tbl.getTable(i).getTablename();
                stringtablelist[i][2] = String.valueOf(tbl.getTable(i).getPlayersno());
            }
            return stringtablelist;
        } catch (RemoteException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean subscribeToTable(int id) throws RemoteException{
        try {
            mynumber = srt.getTableList().getTable(id).getPlayersno();
            try{
                LocateRegistry.createRegistry(registryport);
                Naming.rebind("//localhost:" + registryport + "/PeerRMITresette" + mynumber, peer);
            }catch(Exception e){
                System.out.println("e' fallita la creazione del registro, speriamo che sia perche' e' gia' stato creato");
                Naming.rebind("//localhost:" + registryport + "/PeerRMITresette" + mynumber, peer);
            }
            if (!srt.subscribeToTable(id, registryport)) {
                        Naming.unbind("//localhost:" + registryport + "/PeerRMITresette" + mynumber);
                        mynumber = -1;
                        return false;
                    } else {
                        return true;
                    }
        } catch (Exception ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void startTheTable(Deck deck, String[] iplist, int firstplayer) throws RemoteException {
        try {
            System.out.println("Acquairing permits from startthetable");
            sem.acquire();
            System.out.println("At the startTheTable method the parameter deck is: " + deck.toString());
            this.deck = deck;
            System.out.println("At the startTheTable method the global deck is: " + this.deck.toString());
            peerlist = new PeerLibraryRMITresetteInterface[4];
            for (int i = 0; i < 4; i++)
                if(i != mynumber)
                    peerlist[i] = (PeerLibraryRMITresetteInterface) Naming.lookup("//" + iplist[i] + "/PeerRMITresette" + i);
                else
                    peerlist[i] = (PeerLibraryRMITresetteInterface) Naming.lookup("//" + "127.0.0.1" + "/PeerRMITresette" + i);
            this.firstplayer = firstplayer;
            if (firstplayer == mynumber)
                token = true;
            Method m = peergame.getClass().getDeclaredMethod("wakeUp");
            m.invoke(peergame);
            m = peergame.getClass().getDeclaredMethod("manageTKL", boolean.class);
            m.invoke(peergame, token);
            System.out.println("I'm exiting from the library");
            crashtimer = new Timer();
            crashtimertarget = (mynumber + 1) % 4;
            if (mynumber != 0){
                crashtimer = new Timer();
                crashtimer.schedule(new CrashControlTimer((mynumber + 1) % 4), 5000 * (4 - mynumber), 5000 * (4 - mynumber));
            }
            System.out.println("Il crashtimermultiplier dalla startTheTable e': " + (4 - mynumber) + " e il target e': " + crashtimertarget);
        } catch (Exception ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Releasing permits from startthetable");
        sem.release();
    }
    
    //in questa funzione viene cambiata la ontablecards[mynumber], il token,
    //se esce un eccezione la peerlist del peer crashato(di cui comunque lui deve provvedere), il crashtimertarget e il crashmultiplier
    public boolean broadCastCard(Card card, String chat){
        try {
            System.out.println("Acquairing permits from broadcastcard");
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        //controllo se ho il token e se la carta non e' null
        if (this.token && card != null){
           // System.out.println("I have the token");
            //controllo se la carta e' presente nel mio attuale deck
            if (deck.whatIsTheAddressOfThisCard(card, mynumber)[0] != -1){
                //System.out.println("The card is in the deck");
                //controllo se la carta puo' essere giocata
                if ((mynumber == firstplayer) || GameRules.isItAPlayableCard(card, ontablecards[firstplayer], deck.getDeck()[mynumber])){
                    System.out.println("I throw the: " + card.getRank().name() + " of " + card.getSuit().name());
                    ontablecards[mynumber] = card;
                }else{
                    System.out.println("Releasing permits from broadcastcard");
                    sem.release();
                    return false;
                }
            }else{
                System.out.println("This card ( the " + card.getRank() + " of " + card.getSuit() + ") does not exist in this deck");
                System.out.println("Releasing permits from broadcastcard");
                sem.release();
                return false;
            }
            //nuovo controllo per il fine mano
            int nextplayer = ((mynumber + 1) % 4);
            //controllo se la mano e' finita
            token = false;
            if (nextplayer == firstplayer){
                nextplayer = GameRules.getHandWinner(ontablecards, firstplayer);
            }
            ontablecards[mynumber] = null;
            //mando le carte a tutti gli altri giocatori
            int reali = -1;
            for (int i = 1 + nobots ; i < 4; i++)
                    try {
                        //evito di mandare messaggi ripetuti a chi ha uno o piu' bot
                        reali = (mynumber + i) % 4;
                        System.out.println("reali: " + reali + " nobots: " + nobots);
                        if (!((reali == 0) && peerlist[3].equals(peerlist[0])) && !((reali != 0) && (peerlist[reali].equals(peerlist[reali - 1]))))
                            while (!peerlist[reali].getCardFromAnotherPlayer(card, chat))
                                Thread.sleep(100);
                        //Thread.sleep(5000);
                    } catch (Exception ex) {
                        //Qualcuno e' crashato, vedo se devo creare il bot per sostituirlo
                        System.out.println("Si, qualcuno e' crashato");
                        if (reali == ((mynumber + 1) % 4)){
                            System.out.println("Sostituisco il giocatore crashato");
                            nobots = 1;
                            peerlist[((mynumber + 1) % 4)] = peerlist[mynumber];
                            //ontablecards[mynumber] = card;
                            for (int j = 1; j <= 2; j++)
                                try {
                                    peerlist[((mynumber + nobots) + j) % 4].someoneCrashed(mynumber, nobots);
                                } catch (RemoteException rex) {
                                    Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            //ontablecards[mynumber] = null;
                            //aggiorno i timer
                            crashtimer.cancel();
                            crashtimer.purge();
                            crashtimertarget = (crashtimertarget + 1) % 4;
                            crashtimer = new Timer();
                            crashtimer.schedule(new CrashControlTimer(crashtimertarget), 5000 * 2, 5000 * 2);
                            System.out.println("Il crashtimermultiplier dalla broadCastCard e': 2 e il target e': " + crashtimertarget);
                        }
                    }
            System.out.println("Releasing permits from broadcastcard");
            sem.release();
            try {
                peerlist[mynumber].getCardFromAnotherPlayer(card, chat);
                peerlist[nextplayer].getToken();
            } catch (RemoteException ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }else{
            System.out.println("Releasing permits from broadcastcard");
            sem.release();
            return false;
        }
    }
    
    //in questa funzione vengono cambiati: ontablecards[whoisplaying], whoisplaying, firstplayer, bottoken, noactivebot, points, killati i timer
    //e risettati con un nuovo crashtimemultiplier
    @Override
    public boolean getCardFromAnotherPlayer(Card card, String chat){
        //Controllo se questo peer ha finito di fare le operazioni di creazione
        /*if (imready == false)
            return false;*/
        try {
            System.out.println("The sem permits avaiable are: " + sem.availablePermits());
            System.out.println("Acquairing permits from getcardfromanotherplayer");
            sem.acquire();
            System.out.println("Getting a card from another player");
            ontablecards[whoisplaying] = card;
            whoisplaying = (whoisplaying + 1) % 4;
            System.out.println("The last player who sent the card is: " + whoisplaying + " - 1, and the firstplayer is: " + firstplayer);
            //nel caso in cui non ci siano null sul tavolo allora la mano e' finita
            int lasthandwinner = -1;
            if (whoisplaying == firstplayer){
                firstplayer = GameRules.getHandWinner(ontablecards, firstplayer);
                whoisplaying = firstplayer;
                System.out.println("The handwinner is: " + firstplayer);
                lasthandwinner = firstplayer;
                if ((firstplayer == 1) || (firstplayer == 3))
                        points[1] = GameRules.getHandPoints(ontablecards);
                    else
                        points[0] = GameRules.getHandPoints(ontablecards);
                for (int i = 0; i < 4; i++)
                    playedcards.add(ontablecards[i]);
                for(int j = 0; j < 4; j++)
                    ontablecards[j] = null;
            }
            //Aggiorno i crashtimer
            crashtimer.cancel();
            crashtimer.purge();
            int crashtimemultiplier = 0;
            if (whoisplaying > mynumber)
                crashtimemultiplier = whoisplaying - mynumber;
            else
                crashtimemultiplier = (4 - mynumber) + whoisplaying;
            if ((crashtimemultiplier != 0) && (crashtimemultiplier != 4)){
                crashtimer = new Timer();
                crashtimer.schedule(new CrashControlTimer(crashtimertarget), 5000 * crashtimemultiplier, 5000 * crashtimemultiplier);
            }
            System.out.println("Il crashtimermultiplier dalla getCardFromAnotherplayer e': " + crashtimemultiplier + " e il target e': "
                    + crashtimertarget + " whoisplaying is: " + whoisplaying);
            
            MoveThread mt = new MoveThread(card, lasthandwinner, chat);
            new Thread(mt).start();
        } catch (Exception ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Releasing permits from getcardfromanotherplayer");
        sem.release();
        return true;
    }
    
    public void amIReady(boolean ready){
        imready = ready;
    }
    
    
    public boolean isToken() {
        return token;
    }
    
    
    public Deck getDeck() {
        return deck;
    }

    public int getMynumber() {
        return mynumber;
    }
    
    public void setPeergame(T peergame) {
        this.peergame = peergame;
    }
    
    public void unregisterRMI(){
        try {
            System.out.println("Is it done? " + UnicastRemoteObject.unexportObject(peer, true));
            Naming.unbind("//localhost:" + registryport + "/PeerRMITresette" + mynumber);
            for (String s: Naming.list("//localhost:" + registryport))
                System.out.println(s);
            if (crashtimer != null){
                crashtimer.cancel();
                crashtimer.purge();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void amIAlive(boolean token){
        if (whoisplaying == mynumber)
            this.token = token;
        
        for (int i = 1; i <= nobots; i++){
            if ((mynumber + i) == whoisplaying){
                noactivebot = whoisplaying;
                BotThread bt = new BotThread();
                new Thread(bt).start();
                break;
            }
        }
    }
    
    //Questa funzione cambia la peerlist per i crashati e il crashtimemultiplier
    @Override
    public void someoneCrashed(int botmaster, int nobot){
        try {
            System.out.println("Acquairing permits from someonecrashed");
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Qualcuno e' crashato, il giocatore " + botmaster + " ha ora " + nobot + " bot");
        for (int i = 1; i <= nobot; i++)
            peerlist[(botmaster + i) % 4] = peerlist[botmaster];
        crashtimer.cancel();
        crashtimer.purge();
        //Questo sarebbe un performance improvement che sembrava andare, ma per adesso viene commentato
        /*int i = nobot;
        while (i > 0){
            if (crashtimertarget == ((botmaster + i) % 4))
                crashtimertarget = botmaster;
            i--;
        }*/
        int crashtimemultiplier = 0;
        if (whoisplaying > mynumber)
            crashtimemultiplier = whoisplaying - mynumber;
        else
            crashtimemultiplier = (4 - mynumber) + whoisplaying;
        if (crashtimemultiplier != 0){
            crashtimer = new Timer();
            crashtimer.schedule(new CrashControlTimer(crashtimertarget), 5000 * crashtimemultiplier, 5000 * crashtimemultiplier);
        }
        System.out.println("Il crashtimermultiplier dalla someoneCrashed e': " + crashtimemultiplier + " e il target e': " + crashtimertarget);
        System.out.println("Releasing permits from someonecrashed");
        sem.release();
    }
    
    @Override
    public void firstPlayerCrashed(){
        try {
            System.out.println("Acquairing permits from firstplayercrashed");
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        //I'm the bloody first player now!
        firstplayer = 0;
        noactivebot = 0;
        nobots = 0;
        //Controllo quanti player dopo di me sono crashati
        int i;
        for(i = 1; i < 4; i++)
            try {
                peerlist[(mynumber + i) % 4].amIAlive(false);
            } catch (RemoteException ex) {
                nobots++;
                peerlist[(mynumber + i) % 4] = peerlist[mynumber];
            }
        System.out.println("Sono crashati " + nobots + " giocatori dopo di me");
        if (nobots != 3){
        //Comunico agli altri player in quanti sono crashati dopo di me e che io li sostituiro
            crashtimertarget = (mynumber + nobots + 1) % 4;
            for (i = 1; i <= (3 - nobots); i++)
                try {
                    peerlist[((mynumber + nobots) + i) % 4].someoneCrashed(mynumber, nobots);
                } catch (RemoteException ex) {
                    Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
                }
            sem.release();
            BotThread bt = new BotThread();
            new Thread(bt).start();
        }else{
            try {
                sem.release();
                System.out.println("La firstplayer crashed si e' accorta che i bot sono 3 e dichiara la win");
                Method m = peergame.getClass().getDeclaredMethod("unexpectedWin");
                m.invoke(peergame);
                return;
            } catch (Exception ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Releasing permits from firstplayercrashed");
    }

    @Override
    public void getToken() throws RemoteException {
        try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("GetToken, mynumber is: " + mynumber + ", the nobots is: " + nobots + " and the whoisplaying is: " + whoisplaying);
        if (mynumber == whoisplaying){
            token = true;
            try{
                Method m;
                m = peergame.getClass().getDeclaredMethod("manageTKL", boolean.class);
                m.invoke(peergame, token);
            } catch (Exception ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            for(int i = 1; i <= nobots; i++)
                if (((mynumber + i) % 4) == whoisplaying){
                    bottoken = true;
                    noactivebot = whoisplaying;
                    break;
                }
        if (bottoken){
            //Il bottoken serve ad evitare race condition
            bottoken = false;
            BotThread bt = new BotThread();
            new Thread(bt).start();
        }
        sem.release();            
    }
    
    @Override
    public Card getLastOnTableCard(int index){
        return ontablecards[index];
    }
    
    @Override
    public void setOnTableCard(Card card, int index){
        /*if (ontablecards[index] == null){
            ontablecards[index] = card;
            MoveThread mt;
            if ((whoisplaying + 1 % 4) != firstplayer)
                MoveThread mt = new MoveThread(card, -1, "");
            
            new Thread(mt).start();
            whoisplaying = (whoisplaying + 1) % 4;
        }*/
    }
    
    public Double[] getPoints(){
        return points;
    }
    
    private class MoveThread<T> implements Runnable {
        private Card card = null;
        private int lasthandwinner = -1;
        private String chat = "";
        
        public MoveThread(Card card, int lasthandwinner, String chat){
            this.card = card;
            this.lasthandwinner = lasthandwinner;
            this.chat = chat;
        }

        @Override
        public void run() {
            try {
                System.out.println("Acquairing permits from movethread");
                sem.acquire();
                int whoplayed = deck.whatIsTheAddressOfThisCard(card)[0];
                Method m = peergame.getClass().getDeclaredMethod("newMove", Card.class, int.class, int.class);
                m.invoke(peergame, card, whoplayed, lasthandwinner);
                m = peergame.getClass().getDeclaredMethod("remoteSetStatusText", String.class);
                m.invoke(peergame, "Player " + whoplayed + " says: " + chat);
                m = peergame.getClass().getDeclaredMethod("manageTKL", boolean.class);
                m.invoke(peergame, false);
                System.out.println("Releasing permits from movethread");
                sem.release();
            } catch (Exception ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private class BotThread implements Runnable {

        @Override
        public void run() {
            int nextplayer = 0;
            //String chatmessage = "Hi, I'm a Bot, I'm here to replace player " + noactivebot;
            try {
                System.out.println("Acquairing permits from botthread");
                sem.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Mega imba AI....da migliorare
            System.out.println("Hi, I'm a Bot, I'm here to replace player " + noactivebot +
                    " this time I need to play a card witch match the player " + firstplayer + " card Suit");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<Card> playablecards = new ArrayList<Card>();
            for (int i = 0; i < 10; i++){
                if (deck.getDeck()[noactivebot][i] != null)
                    playablecards.add(deck.getDeck()[noactivebot][i]);
            }
            System.out.println("Le playablecards che passo sono:");
            Card[] staticplayablecards = new Card[playablecards.size()];
            for (int i = 0; i < playablecards.size(); i++){
                staticplayablecards[i] = playablecards.get(i);
                System.out.println(i + "- playable e' " + staticplayablecards[i].getRank() + " of " + staticplayablecards[i].getSuit());
            }
            System.out.println("Le playedcards che passo sono:");
            Card[] staticplayedcards = new Card[playedcards.size()];
            for (int i = 0; i < playedcards.size(); i++){
                staticplayedcards[i] = playedcards.get(i);
                System.out.println(i + "- played e' " + staticplayedcards[i].getRank() + " of " + staticplayedcards[i].getSuit());
            }
            
            Card card;
            if (noactivebot == firstplayer)
                card = GameRules.botInitSelectionCard(staticplayablecards, staticplayedcards);
            else{
                List<Card> cardontable = new ArrayList<Card>();
                for (int i = 0; i < 4; i++){
                    if (ontablecards[(firstplayer + i) % 4] != null)
                        cardontable.add(ontablecards[(firstplayer + i) % 4]);
                }
                Card[] staticcardontable = new Card[cardontable.size()];
                System.out.println("Le cardontable che passo sono:");
                for (int i = 0; i < cardontable.size(); i++){
                    staticcardontable[i] = cardontable.get(i);
                    System.out.println(i + "- cardontable e' " + staticcardontable[i].getRank() + " of " + staticcardontable[i].getSuit());
                }
                card = GameRules.botSelectionCard(staticplayablecards, staticplayedcards, staticcardontable);
            }
            String chatmessage = GameRules.getBotMessage();
            
            for (int i = 0; i < deck.getDeck()[noactivebot].length; i++){
                card = deck.getDeck()[noactivebot][i];
                if ((card != null) && ((noactivebot == firstplayer) ||
                        GameRules.isItAPlayableCard(card, ontablecards[firstplayer], deck.getDeck()[noactivebot]))){
                    ontablecards[noactivebot] = card;
                    break;
                }
            }
            if (card != null){
                System.out.println("The " + card.getRank() + " Of " + card.getSuit() + " will be ok");
                nextplayer = ((noactivebot + 1) % 4);
                if (nextplayer == firstplayer){
                    nextplayer = GameRules.getHandWinner(ontablecards, firstplayer);
                }
                ontablecards[noactivebot] = null;
                noactivebot = -1;
                int reali = -1;
                //for (int i = 3; i >= 0; i--)
                for (int i = 1 + nobots; i < 4; i++)
                    try {
                        reali = (mynumber + i) % 4;
                        if (!((reali == 0) && peerlist[3].equals(peerlist[0])) && !((reali != 0) && (peerlist[reali].equals(peerlist[reali - 1]))))
                                while (!peerlist[reali].getCardFromAnotherPlayer(card, chatmessage))
                                    Thread.sleep(100);
                    } catch (Exception ex) {
                        //Qualcuno e' crashato mentre stava giocando il bot
                        System.out.println("Si, qualcuno e' crashato mentre stava giocando il bot");
                        if (reali == ((mynumber + nobots + 1) % 4)){
                            System.out.println("Sostituisco il giocatore crashato(mentre stava giocando il bot)");
                            nobots++;
                            if (nobots != 3){
                                peerlist[((mynumber + nobots) % 4)] = peerlist[mynumber];
                                //Considerato che se si arriva a questo punto significa che quello che vado a sostituire e' quello subito dopo il bot
                                //i nostri stati sono ancora tutti coerenti, perche' ancora non ho inviato le carte a nessuno
                                try {
                                    peerlist[((mynumber + nobots) + 1) % 4].someoneCrashed(mynumber, nobots);
                                } catch (RemoteException rex) {
                                    Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                //ontablecards[noactivebot] = null;
                                //il crashtimertarget qui non era aggiornato, adesso lo aggiorno perche' altrimenti si sputtana di brutto
                                crashtimer.cancel();
                                crashtimer.purge();
                                crashtimertarget = (crashtimertarget + 1) % 4;
                                crashtimer = new Timer();
                                crashtimer.schedule(new CrashControlTimer(crashtimertarget), 5000, 5000);
                                System.out.println("Il crashtimermultiplier dalla broadCastCard e': 1 e il target e': " + crashtimertarget);
                            }else
                                try {
                                    System.out.println("Il bot ha capito che ci sono 3 bot e quindi dichiara la win");
                                    Method m = peergame.getClass().getDeclaredMethod("unexpectedWin");
                                    m.invoke(peergame);
                                    return;
                                } catch (Exception ex1) {
                                    Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                        }
                    }
            }
            System.out.println("Releasing permits from botthread");
            sem.release();
            try {
                peerlist[mynumber].getCardFromAnotherPlayer(card, chatmessage);
                peerlist[nextplayer].getToken();
            } catch (RemoteException ex) {
                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private class CrashControlTimer extends TimerTask{
        
        //private int whotowatch = -1;
        
        public CrashControlTimer(int whotowatch){
            //this.whotowatch = whotowatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Controllo se il giocatore " + crashtimertarget + " e' ancora vivo");
                if (crashtimertarget == whoisplaying)
                    peerlist[crashtimertarget].amIAlive(true);
                else
                    peerlist[crashtimertarget].amIAlive(false);
            } catch (RemoteException ex) {
                try {
                    System.out.println("Acquairing permits from crashcontroltimer");
                    sem.acquire();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Card lastcard = null;
                System.out.println("Il giocatore " + crashtimertarget + " non risponde bisogna sostituirlo con un bot(il whoisplaying e' " + whoisplaying + ")");
                //A che cazzo serve sto controllo?
                if (crashtimertarget == ((mynumber + nobots + 1) % 4)){
                    System.out.println("Posso sostituirlo");
                    nobots++;
                    int hisbots = 0;
                    if (nobots != 3){
                        peerlist[((mynumber + nobots) % 4)] = peerlist[mynumber];
                        if (crashtimertarget == whoisplaying){
                            System.out.println("Il giocatore " + crashtimertarget + " e' crashato mentre aveva il token");
                            for (int i = (crashtimertarget + 1) % 4; i < 4; i++){
                                try {
                                    lastcard = peerlist[i].getLastOnTableCard(whoisplaying);
                                    break;
                                } catch (RemoteException ex1) {
                                    System.out.println("Il giocatore che ho provato a contattare per fare il controllo di coerenza e' crashato");
                                }
                            }

                        }
                        crashtimertarget = (crashtimertarget + 1) % 4;
                        for (int j = 1 + nobots; j < 4; j++)
                            try {
                                //Vedo se il tipo che e' crashato aveva il token
                                //Se se ne accorge questo che qualcuno e' crashato mentre stava a dare le carte significa che e' quello piu' indietro di tutti
                                peerlist[(mynumber + j) % 4].someoneCrashed(mynumber, nobots);
                            } catch (RemoteException rex) {
                                hisbots++;
                            }
                        //Aggiorno i timer
                        crashtimer.cancel();
                        if (hisbots + nobots == 3)
                            try {
                                System.out.println("Il crashcontrol timer si accorge che il numero di bot del giocatore piu' il numero dei bot del"
                                        + " giocatore crashato equivale a 3 e dichiara la win");
                                Method m = peergame.getClass().getDeclaredMethod("unexpectedWin");
                                m.invoke(peergame);
                                return;
                            } catch (Exception ex1) {
                                Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        int crashtimemultiplier = 0;
                        if (whoisplaying > ((mynumber + nobots) % 4))
                            crashtimemultiplier = whoisplaying - ((mynumber + nobots) % 4);
                        else
                            crashtimemultiplier = (4 - ((mynumber + nobots) % 4)) + whoisplaying;
                        if (crashtimemultiplier != 0){
                            crashtimer = new Timer();
                            crashtimer.schedule(new CrashControlTimer(crashtimertarget), 5000 * crashtimemultiplier, 5000 * crashtimemultiplier);
                        }
                        System.out.println("Il crashtimermultiplier dalla crashControlTimer e': " + crashtimemultiplier + " e il target e': " + crashtimertarget);
                        if ((whoisplaying == ((mynumber + nobots) % 4)) && (lastcard == null)){
                            System.out.println("He is the token one!");
                            noactivebot = (mynumber + nobots) % 4;
                            BotThread bt = new BotThread();
                            new Thread(bt).start();
                        }
                    }else
                        try {
                            System.out.println("Il crashtimer si e' accorto che i bot sono diventati 3 e dichiara la win");
                            Method m = peergame.getClass().getDeclaredMethod("unexpectedWin");
                            m.invoke(peergame);
                            return;
                        } catch (Exception ex1) {
                            Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                }
                System.out.println("Releasing permits from crashcontroltimer");
                sem.release();
                if (lastcard != null){
                    System.out.println("La lastcard e' diversa da null");
                    try {
                        //peerlist[(mynumber + 3) % 4].setOnTableCard(lastcard, whoisplaying);
                        peerlist[(mynumber + 3) % 4].getCardFromAnotherPlayer(lastcard, "");
                        peerlist[mynumber].getCardFromAnotherPlayer(lastcard, "");
                        //controllo se la mano e' finita
                        System.out.println("I give the token to: " + whoisplaying);
                        peerlist[whoisplaying].getToken();
                    } catch (RemoteException ex1) {
                        Logger.getLogger(PeerLibraryRMITresette.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        
    }
    
}
