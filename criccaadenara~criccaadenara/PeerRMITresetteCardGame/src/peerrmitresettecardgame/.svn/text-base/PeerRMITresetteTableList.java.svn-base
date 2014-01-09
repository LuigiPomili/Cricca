/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peerrmitresettecardgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import peerlibraryrmitresette.PeerLibraryRMITresette;
//import serverrmitresette.TablesListInterface;

/**
 *
 * @author paolo
 */
public class PeerRMITresetteTableList extends JFrame {
    
    private JButton jbutRefresh;
    private JButton jbutConnect;
    private JButton jbutCreate;
    private JLabel jlblNewTableName;
    private JLabel jlblIn;
    private JLabel jlblRegisterPortNo;
    private JPanel jpnlActive;
    private JPanel jpnlServer;
    private JPanel jpnlCreate;
    private JScrollPane jscrTable;
    private JTable jtlbTableList;
    private JTextField jtxtServer;
    private JTextField jtxtNewTableName;
    private JTextField jtxtPortServer;
    private JTextField jtxtRegisterPort;
    private DefaultTableModel modello;
    //vecchi
    private PeerLibraryRMITresette peerlib = null;
    private String server;
    private Integer registerPort;

    public PeerRMITresetteTableList() {
        initcomponent();
    }
    
    private void initcomponent(){
        
        /*try {
            peerlib = new PeerLibraryRMITresette("localhost:1099", 1099, null);
        } catch (RemoteException ex) {
            Logger.getLogger(PeerRMITresetteTableList.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        jpnlActive = new JPanel();
        jbutRefresh = new JButton();
        jscrTable = new JScrollPane();
        jpnlServer = new JPanel();
        jtxtServer = new JTextField();
        jbutConnect = new JButton();
        jpnlCreate = new JPanel();
        jlblNewTableName = new JLabel();
        jlblIn = new JLabel();
        jlblRegisterPortNo = new JLabel();
        jtxtNewTableName = new JTextField();
        jtxtPortServer = new JTextField();
        jtxtRegisterPort = new JTextField();
        jbutCreate = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cricca a denara - Tresette Game");
        setBounds(new Rectangle(300, 200, 500, 400));
        setName("JFramePrincipal");

        jpnlActive.setBorder(BorderFactory.createTitledBorder(null, "Active tables:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("DejaVu Sans Mono", 1, 14)));
        jbutRefresh.setText("Refresh");
        jbutRefresh.setEnabled(false);
        jbutRefresh.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent evt) {
                onMouseClickButtonRefresh(evt);
            }
        
        });
        
        modello = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        jtlbTableList = new JTable(modello);
        jtlbTableList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                onMouseClickListItem(evt);
            }
        });
        jtlbTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modello.addColumn("Table Id");
        modello.addColumn("Table name");
        modello.addColumn("Players No.");
        jscrTable.setViewportView(jtlbTableList);

        GroupLayout jPanelActiveLayout = new GroupLayout(jpnlActive);
        jpnlActive.setLayout(jPanelActiveLayout);
        jPanelActiveLayout.setHorizontalGroup(
            jPanelActiveLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActiveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActiveLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanelActiveLayout.createSequentialGroup()
                        .addComponent(jbutRefresh)
                        .addGap(27, 27, 27))
                    .addGroup(jPanelActiveLayout.createSequentialGroup()
                        .addComponent(jscrTable, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanelActiveLayout.setVerticalGroup(
            jPanelActiveLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanelActiveLayout.createSequentialGroup()
                .addComponent(jscrTable, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbutRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpnlServer.setBorder(BorderFactory.createTitledBorder(null, "Server location:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("DejaVu Sans Mono", 1, 14)));
        jtxtServer.setText("localhost");
        jtxtPortServer.setText("1099");
        jtxtRegisterPort.setText("1099");
        jlblIn.setText(":");
        jlblRegisterPortNo.setText("Register Port No:");
        jbutConnect.setText("Connect");
        jbutConnect.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                onMouseClickButtonConnect(evt);
            }
        });
        
        GroupLayout jPanelServerLayout = new GroupLayout(jpnlServer);
        jpnlServer.setLayout(jPanelServerLayout);
        jPanelServerLayout.setHorizontalGroup(
            jPanelServerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelServerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelServerLayout.createSequentialGroup()
                        .addComponent(jtxtServer, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblIn, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelServerLayout.createSequentialGroup()
                        .addComponent(jlblRegisterPortNo)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtRegisterPort, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanelServerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelServerLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jbutConnect))
                    .addGroup(jPanelServerLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtPortServer, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelServerLayout.setVerticalGroup(
           jPanelServerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelServerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblIn,GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jtxtPortServer)
                    .addComponent(jtxtServer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanelServerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jbutConnect, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jlblRegisterPortNo, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtRegisterPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        
        jpnlCreate.setBorder(BorderFactory.createTitledBorder(null, "Create new table", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("DejaVu Sans Mono", 1, 14)));
        jlblNewTableName.setText("Table's name");
        jbutCreate.setText("Create");
        jbutCreate.setEnabled(false);
        jbutCreate.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                onMouseClickButtonCreate(evt);
            }
        });

        GroupLayout jPanelCreateLayout = new GroupLayout(jpnlCreate);
        jpnlCreate.setLayout(jPanelCreateLayout);
        jPanelCreateLayout.setHorizontalGroup(
            jPanelCreateLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCreateLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtNewTableName, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(jlblNewTableName, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(jbutCreate, GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelCreateLayout.setVerticalGroup(
            jPanelCreateLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanelCreateLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jlblNewTableName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtNewTableName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbutCreate)
                .addGap(52, 52, 52))
        );

        // principal layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlServer, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlActive, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnlCreate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpnlActive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jpnlServer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(jpnlCreate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pack();
    }

    private void onMouseClickButtonCreate(MouseEvent evt) {
        String nametable = jtxtNewTableName.getText();
        if (nametable.equals("")) {
            JOptionPane.showMessageDialog(getContentPane(), "Error: You must insert a table's name", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                peerlib = new PeerLibraryRMITresette(server, registerPort, null);
            } catch (RemoteException ex) {
                Logger.getLogger(PeerRMITresetteTableList.class.getName()).log(Level.SEVERE, null, ex);
            }
            peerlib.createTable(jtxtNewTableName.getText());
            jtxtNewTableName.setText("");
            this.setVisible(false);
            TresetteCardGame game = new TresetteCardGame(this, peerlib);
        }

    }

    private void onMouseClickListItem(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int rowclicked = jtlbTableList.getSelectedRow();
            try {
                this.setVisible(false);
                peerlib = new PeerLibraryRMITresette(server, registerPort, null);
                TresetteCardGame game = new TresetteCardGame(this, peerlib);
                peerlib.subscribeToTable(rowclicked);
            } catch (RemoteException ex) {
                Logger.getLogger(PeerRMITresetteTableList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void refreshTableList() {
        for (int i = 0; i < modello.getRowCount(); i++) {
            modello.removeRow(i);
        }
        String[][] tbl = peerlib.getTableList();
        for (int i = 0; i < tbl.length; i++) {
            modello.addRow(new Object[]{tbl[i][0], tbl[i][1], tbl[i][2]});
        }
    }
    
    private void onMouseClickButtonRefresh(MouseEvent evt) {
         refreshTableList();
    }
    
    private void onMouseClickButtonConnect(MouseEvent evt) {
       server = jtxtServer.getText().trim() + ":" + jtxtPortServer.getText().trim();
       String register = jtxtRegisterPort.getText().trim();
       registerPort = Integer.parseInt(register);
       /*System.out.println("Server location: " + server);
       System.out.println("Register Port: "+ registerPort);*/
       //if (server.equals("localhost:1099")){
           try {
               //peerlib = new PeerLibraryRMITresette("localhost:1099", 1099, null);
               peerlib = new PeerLibraryRMITresette(server, registerPort, null);
           } catch (RemoteException ex) {
                Logger.getLogger(PeerRMITresetteTableList.class.getName()).log(Level.SEVERE, null, ex);
           }
           jbutCreate.setEnabled(true);
           jbutRefresh.setEnabled(true);
           jpnlServer.setBorder(BorderFactory.createTitledBorder(null, "Server location: " + server + "   " + "Register Port: " + registerPort, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("DejaVu Sans Mono", 1, 14)));
           jtxtPortServer.setText("");
           jtxtRegisterPort.setText("");
           jtxtServer.setText("");
           jtxtPortServer.setEnabled(false);
           jtxtRegisterPort.setEnabled(false);
           jtxtServer.setEnabled(false);
           jbutConnect.setEnabled(false);
           refreshTableList();
       //}
    }
}
