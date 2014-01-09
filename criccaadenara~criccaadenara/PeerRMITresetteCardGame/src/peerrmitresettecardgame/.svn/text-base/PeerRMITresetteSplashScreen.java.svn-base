/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peerrmitresettecardgame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author paolo
 */
public class PeerRMITresetteSplashScreen extends JFrame{

    public PeerRMITresetteSplashScreen(){
        initComponent();
    }

    private void initComponent(){
        ImageIcon picturetoInsert = new ImageIcon("tresetteSplash.jpg");
        JLabel lblSplashScreen = new JLabel("",picturetoInsert,JLabel.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Cricca a denara - Tresette Game");
        addMouseListener(new MouseAdapter() {@Override
            public void mouseClicked(MouseEvent evt) {
                MouseClick(evt);
            }
        });
        setBounds(new Rectangle(300, 200, 0, 0));
        setName("JFramePrincipal");
        lblSplashScreen.setPreferredSize(new Dimension(600, 400));
        getContentPane().add(lblSplashScreen, BorderLayout.PAGE_START);
        lblSplashScreen.getAccessibleContext().setAccessibleName("JLabelImage");

        pack();
    }
    
    private void MouseClick(MouseEvent evt) {                            
        setVisible(false);
        PeerRMITresetteTableList tableList = new PeerRMITresetteTableList();
        tableList.setVisible(true);
    }         
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PeerRMITresetteSplashScreen splashScreen = new PeerRMITresetteSplashScreen();
        splashScreen.setVisible(true);
    }
}
