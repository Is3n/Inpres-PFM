/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_jchat_pfm;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import reqrep_pfmcop.MessagePFMCOP;

/**
 *
 * @author isen0
 */
public class ThreadReception extends Thread
{
    private String nom;
    private MulticastSocket socketGroupe;
    private JList listeMessages;
    public static DefaultListModel modelListe = new DefaultListModel();
    public ThreadReception (String n, MulticastSocket ms, JList l)
    {
        nom = n; socketGroupe = ms; listeMessages = l;
    }
    
    public void run()
    {
        boolean enMarche = true;
        while (enMarche)
        {
            try
            {
            byte[] buf = new byte[2048];
            DatagramPacket dtg = new DatagramPacket(buf, buf.length);
            socketGroupe.receive(dtg);
            
            byte[] byteMessage = dtg.getData();
            
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(byteMessage));
            StringBuilder buffer = new StringBuilder();
            byte b;
            char endMsg = "\r".toCharArray()[0];
            //System.out.println("TEST 111" );
            while( (b = input.readByte()) != (byte)endMsg ){
                buffer.append((char)b);
                //System.out.println("TEST buffer "+ buffer.toString());
            }
            //System.out.println("TEST buffer "+ buffer.toString());
            
            //System.out.println("TEST 222" );
            MessagePFMCOP rcvMsg = MessagePFMCOP.createMessage(buffer.toString());
            //System.out.println("TEST 333" );
            modelListe.addElement(rcvMsg);
            listeMessages.setModel(modelListe);
            }
            catch (IOException e)
            {
                System.err.println("Erreur dans le thread :-( :" + e.getMessage());
                enMarche = false; // fin
            }
        }
    }
}
