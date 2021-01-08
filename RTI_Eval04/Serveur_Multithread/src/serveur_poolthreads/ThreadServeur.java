/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_poolthreads;

import java.net.*;
import java.io.*;
import reqrep_poolthreads.Requete;
import reqrep_poolthreads.ConsoleServeur;

/**
 *
 * @author isen0
 */
public class ThreadServeur extends Thread
{
    private int port;
    private SourceTaches tachesAExecuter;
    private ConsoleServeur guiApplication;
    private ServerSocket SSocket = null;
    //private int SocketConnectees[] = new int [Integer.parseInt(serveur_poolthreads.FichierConfig.get("NBTHREADSCLIENT"))];
    //private int i;
    public ThreadServeur(int p, SourceTaches st, ConsoleServeur fs)
    {
        port = p; tachesAExecuter = st; guiApplication = fs;
        /*for(i=0; i< SocketConnectees.length; i++)
        {
            SocketConnectees[i]=0;
        }*/
    }
    public void run()
    {
        try
        {
            SSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); System.exit(1);
        }
        // Démarrage du pool de threads
        for (int j=0; j<Integer.parseInt(serveur_poolthreads.FichierConfig.get("NBTHREADSCLIENT")); j++) // Nbr max de threads client est une propriété du fichier de config
        {
            ThreadClient thr = new ThreadClient (tachesAExecuter, "Thread du pool n°" +
            String.valueOf(j));
            thr.start();
        }
        FichierLog.UpdateFich("Pool de "+serveur_poolthreads.FichierConfig.get("NBTHREADSCLIENT")+" threads créé" );
        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted())
        {
            try
            {
                System.out.println("************ Serveur en attente");
                CSocket = SSocket.accept();
                
                /*for (i=0; i<Integer.parseInt(serveur_poolthreads.FichierConfig.get("NBTHREADSCLIENT")) && SocketConnectees[i]!=0; i++);
                if(i==Integer.parseInt(serveur_poolthreads.FichierConfig.get("NBTHREADSCLIENT")))
                {
                    System.err.println("Pas de connexion disponible !!!");
                    //CSocket.close();
                    guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+
                "#Connection denied#thread serveur");
                }
                else
                {
                    System.out.println("Connexion établie sur la socket "+i);
                    FichierLog.UpdateFich("Connexion établie sur la socket "+i);
                    guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+
                "#accept#thread serveur");
                    /*synchronized(this)
                            {
                                SocketConnectees[i] = 1;
                            }
                }*/
            }
            catch (IOException e)
            {
                System.err.println("Erreur d'accept ! ? [" + e.getMessage() + "]"); System.exit(1);
            }
            ObjectInputStream ois=null;
            Requete req = null;
            try
            {
                ois = new ObjectInputStream(CSocket.getInputStream());
//                System.out.println("TEST1");
                req = (Requete)ois.readObject();
                System.out.println("Requete lue par le serveur, instance de " +
                req.getClass().getName());
                FichierLog.UpdateFich("Requete lue par le serveur, instance de " + req.getClass().getName());
                
                Runnable travail = req.createRunnable(CSocket, guiApplication);
                if (travail != null)
                {
                    tachesAExecuter.recordTache(travail);
                    System.out.println("Travail mis dans la file");
                }
                else System.out.println("Pas de mise en file");
            }
            catch (ClassNotFoundException e)
            {
                System.err.println("Erreur de def de classe [" + e.getMessage() + "]");
            }
            catch (IOException e)
            {
                System.err.println("Erreur ? [" + e.getMessage() + "]");
            }
//            Runnable travail = req.createRunnable(CSocket, guiApplication);
//            if (travail != null)
//            {
//                tachesAExecuter.recordTache(travail);
//                System.out.println("Travail mis dans la file");
//            }
//            else System.out.println("Pas de mise en file");
        }
    }
}