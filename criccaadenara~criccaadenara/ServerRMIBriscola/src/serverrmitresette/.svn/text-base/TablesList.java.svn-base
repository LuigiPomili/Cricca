/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrmitresette;

import commonclasslibrary.TableRecord;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import serverinterfacelibrary.TableRecordInterface;
import serverinterfacelibrary.TablesListInterface;

/**
 *
 * @author spi
 */
public class TablesList extends UnicastRemoteObject implements TablesListInterface {

    protected List<TableRecord> tbl;

    public TablesList() throws RemoteException{
        super();
        tbl = new ArrayList<TableRecord>();
    }
    
    /**
     * Get the value of tbl
     *
     * @return the value of tbl
     */
    @Override
    public List<TableRecord> getTbl() {
        return tbl;
    }

    /**
     * Set the value of tbl
     *
     * @param tbl new value of tbl
     */
    public void addTable(TableRecord tbr) {
        this.tbl.add(tbr);
    }
    
    @Override
    public TableRecordInterface getTable(int index){
        return this.tbl.get(index);
    }
    
    public void removeTable(int id){
        for (int i = 0; i < tbl.size(); i++)
            if (tbl.get(i).getId() == id){
                tbl.remove(i);
                break;
            }
    }
    
}
