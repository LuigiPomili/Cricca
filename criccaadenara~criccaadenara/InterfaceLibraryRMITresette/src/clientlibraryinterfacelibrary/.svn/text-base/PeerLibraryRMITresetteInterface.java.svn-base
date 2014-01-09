/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientlibraryinterfacelibrary;

import commonclasslibrary.Deck;
import commonclasslibrary.Card;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author spi
 */
public interface PeerLibraryRMITresetteInterface extends Remote {
    
    public void startTheTable(Deck deck, String[] iplist, int firstPlayer) throws RemoteException;
    public boolean getCardFromAnotherPlayer(Card card, String chat) throws RemoteException;
    public void firstPlayerCrashed() throws RemoteException;
    public void amIAlive(boolean token) throws RemoteException;
    public void someoneCrashed(int botmaster, int nobot) throws RemoteException;
    public void getToken() throws RemoteException;
    public Card getLastOnTableCard(int index) throws RemoteException;
    public void setOnTableCard(Card card, int index) throws RemoteException;
    
}
