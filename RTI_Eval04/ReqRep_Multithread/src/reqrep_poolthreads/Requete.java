/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_poolthreads;

import java.net.*;

/**
 *
 * @author isen0
 */
public interface Requete
{
// Ce qui va être exécuté doit connaître la socket du client distant
// ainsi que le GUI qui affiche les traces
    public Runnable createRunnable (Socket s, ConsoleServeur cs);
}
