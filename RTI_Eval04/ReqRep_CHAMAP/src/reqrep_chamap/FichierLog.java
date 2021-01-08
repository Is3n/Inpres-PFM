/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_chamap;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author isen0
 */
public class FichierLog {
    
    public FichierLog() {
    }   
    
    public static void UpdateFich(String ligne)
    {
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = form.format(new Date());
        String nomfich = "logsCHAMAP.txt";
        try 
        {
            FileWriter fw = new FileWriter(nomfich, true);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write("[" + date + "] : " + ligne);
            bf.newLine();
            bf.close();
        } 
        catch (FileNotFoundException e)
        {
            System.out.println("Fichier non trouvé ! " + e.getMessage());
        } 
        catch (IOException ex) 
        {
            ex.getMessage();
        }
    }
}
