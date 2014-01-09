/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuochiartificio;

import java.applet.Applet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import javax.swing.JFrame;
import java.io.IOException;

/**
 *
 * @author lele
 */
public class MainClass {

    public static void main(String[] args) {
        writeToFile("team 1");
        
        try {
            Process process = Runtime.getRuntime().exec("appletviewer applet_testo_fuochiart/applet.htm");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeToFile(String winner) {

        BufferedWriter bufferedWriter = null;

        try {
            File conf = new File("applet_testo_fuochiart/applet.htm");
            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter(conf));

            //Start writing to the output stream
            bufferedWriter.write("<APPLET CODE=\"FireWorks.class\" WIDTH=600 HEIGHT=400>");
            bufferedWriter.newLine();
            bufferedWriter.write("<PARAM NAME=\"Text\" VALUE=\" " + winner + "   W I N ! !\">");
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
    }
}
