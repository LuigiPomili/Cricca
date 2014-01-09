/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrmitresette;

import clientlibraryinterfacelibrary.PeerLibraryRMITresetteInterface;
import commonclasslibrary.Card;
import commonclasslibrary.Deck;
import commonclasslibrary.TableRecord;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverinterfacelibrary.ServerRMITresetteInterface;
import serverinterfacelibrary.TablesListInterface;

/**
 *
 * @author spi
 */
// java -cp /home/spi/NetBeansProjects/ServerRMIBriscola/build/classes/serverrmitresette:/home/spi/NetBeansProjects/ServerRMIBriscola/dist/ServerRMITresette.jar -Djava.security.policy=/home/spi/NetBeansProjects/ServerRMIBriscola/src/serverrmitresette/server.policy serverrmitresette.ServerRMITresette
public class ServerRMITresette extends UnicastRemoteObject implements ServerRMITresetteInterface {

    TablesList tableslist = new TablesList();
    int id = 0;

    public ServerRMITresette() throws RemoteException {
        super();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager installed.");
        } else {
            System.out.println("Security manager already exists.");
        }
        try {
            LocateRegistry.createRegistry(1099);
            ServerRMITresette srt = new ServerRMITresette();
            //ServerRMITresette srtstub = (ServerRMITresette)UnicastRemoteObject.exportObject(srt, 0);
            Naming.rebind("//localhost:1099/ServerRMITresette", srt);
        } catch (Exception ex) {
            Logger.getLogger(ServerRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TablesListInterface getTableList() {
        return tableslist;
    }

    //Ci sarebbe da fare che se sei il creatore del tavolo non puoi fare la subscribe e viceversa, magari si puo' fare anche direttamente
    //da peer il tutto in modo da risparmiare messaggi preziosi
    //da aggiungere la rimozione del tavolo
    @Override
    public boolean subscribeToTable(int id, int port) {
        boolean found = false;
        int i;
        try {
            if (tableslist.getTbl().get(id) != null)
                    found = true;
            if (found && tableslist.getTbl().get(id).addPlayer(UnicastRemoteObject.getClientHost(), port)) {
                if (tableslist.getTbl().get(id).getPlayersno() == 4) {
                    System.out.println("il tavolo: " + id + " sta per iniziare");
                    System.out.println("L'utente con ip: " + UnicastRemoteObject.getClientHost() + " ha fatto la subscribe al tavolo: " + id
                            + " il numero di giocatori e' ora: " + tableslist.getTable(id).getPlayersno());
                    Deck deck = new Deck();
                    deck.getCardRandomCardList();
                    String playerswhocrashed = "";
                    for (int j = 0; j < 4; j++) {
                        System.out.println("Contatto lo user bindato in: " + "//" + tableslist.getTable(id).getIpList()[j] + "/PeerRMITresette" + j);
                        try {
                            PeerLibraryRMITresetteInterface prt =
                                    (PeerLibraryRMITresetteInterface) Naming.lookup("//" + tableslist.getTable(id).getIpList()[j] + "/PeerRMITresette" + j);
                            prt.startTheTable(deck, tableslist.getTable(id).getIpList(), 0);
                        } catch (Exception ex) {
                            System.out.println("Il giocatore " + j + " non e' al momento raggiungibile e verra' quindi sostituito da un bot");
                            playerswhocrashed += j;
                        }
                    }
                    if (!playerswhocrashed.equals(""))
                        System.out.println("I giocatori che sono crashati sono: " + playerswhocrashed);
                    if (playerswhocrashed.length() == 4)
                        System.out.println("Tutti i giocatori del tavolo sono crashati");
                    else {
                        if(playerswhocrashed.contains(Integer.toString(0))){
                            int j = 3;
                            while (playerswhocrashed.contains(Integer.toString(j)))
                                j--;
                            PeerLibraryRMITresetteInterface prt =
                                    (PeerLibraryRMITresetteInterface) Naming.lookup("//" + tableslist.getTable(id).getIpList()[j] + "/PeerRMITresette" + j);
                            prt.firstPlayerCrashed();
                        }
                    }
                    tableslist.removeTable(id);
                    System.out.println("E' stato rimosso il tavolo con id: " + id);
                } else {
                    System.out.println("L'utente con ip: " + UnicastRemoteObject.getClientHost() + " ha fatto la subscribe al tavolo: " + id
                            + " il numero di giocatori e' ora: " + tableslist.getTable(id).getPlayersno());
                }
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServerRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Il tavolo con id: " + id + " e' pieno o inesistente");
        return false;
    }

    @Override
    public void createTable(String tablename, int clientport) {
        try {
            tableslist.addTable(new TableRecord(id, tablename, UnicastRemoteObject.getClientHost(), clientport));
            id++;
            System.out.println("aggiunto il tavolo: " + tablename + " per il client con ip: " + UnicastRemoteObject.getClientHost());
        } catch (Exception ex) {
            Logger.getLogger(ServerRMITresette.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Card[][] getRandomCardList() {
        List<Card> cardslist = new ArrayList<Card>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cardslist.add(new Card(suit, rank));
            }
        }
        Random rnd = null;
        Card[][] cards = new Card[4][10];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                rnd = new Random();
                cards[i][j] = cardslist.remove(rnd.nextInt(cardslist.size()));
            }
        }
        return cards;
    }
}
