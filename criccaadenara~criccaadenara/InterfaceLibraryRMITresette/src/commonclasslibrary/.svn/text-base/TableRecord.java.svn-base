/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commonclasslibrary;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import serverinterfacelibrary.TableRecordInterface;

/**
 *
 * @author spi
 */
public class TableRecord extends UnicastRemoteObject implements TableRecordInterface{

    private int id = -1;
    private String tablename = null;
    private int playersno = 0;
    private String[] iplist = new String[4];
    private int[] portlist = new int[4];

    public TableRecord(int id, String tablename, String ip, int port) throws RemoteException{
        this.id = id;
        this.tablename = tablename;
        this.iplist[0] = ip;
        this.portlist[0] = port;
        playersno++;
    }

    /**
     * Get the value of ip
     *
     * @return the value of ip
     */
    @Override
    public String[] getIpList() {
        String[] iplist2 = new String[4];
        for(int i = 0; i < 4; i++)
            iplist2[i] = iplist[i] + ":" + portlist[i];
        return iplist2;
    }

    /**
     * Set the value of ip
     *
     * @param ip new value of ip
     */
    @Override
    public boolean addPlayer(String ip, int port) {
        if (playersno < 4){
            this.iplist[playersno] = ip;
            this.portlist[playersno] = port;
            playersno++;
            return true;
        }else
            return false;
    }


    /**
     * Get the value of playersno
     *
     * @return the value of playersno
     */
    @Override
    public int getPlayersno() {
        return playersno;
    }

    /**
     * Set the value of playersno
     *
     * @param playersno new value of playersno
     */
    public void setPlayersno(int playersno) {
        this.playersno = playersno;
    }

    /**
     * Get the value of tablename
     *
     * @return the value of tablename
     */
    @Override
    public String getTablename() {
        return tablename;
    }

    /**
     * Set the value of tablename
     *
     * @param tablename new value of tablename
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

}
