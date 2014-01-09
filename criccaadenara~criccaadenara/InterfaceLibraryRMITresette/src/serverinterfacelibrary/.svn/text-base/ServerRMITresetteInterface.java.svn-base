/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverinterfacelibrary;

import serverinterfacelibrary.TablesListInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author spi
 */
public interface ServerRMITresetteInterface extends Remote{
    
    public TablesListInterface getTableList() throws RemoteException;
    
    public boolean subscribeToTable(int id, int port) throws RemoteException;
    
    public void createTable(String tablename, int port) throws RemoteException;
    
}
