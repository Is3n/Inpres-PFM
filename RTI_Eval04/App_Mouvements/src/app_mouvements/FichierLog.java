/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_mouvements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isen0
 */
public class FichierLog {
    
    public FichierLog() {
    }
    
    public static String UpdateLog()
    {
        String str;
        String logfich = new String();
        String nomfich = FichierConfig.getNomsFichs("logs");
        try 
        {
            FileReader fr = new FileReader(nomfich);
            BufferedReader br = new BufferedReader(fr);
            
            while((str = br.readLine()) != null)
            {
                logfich = logfich + "\n" + str;
            }
        } 
        catch (FileNotFoundException e)
        {
            System.out.println("Fichier non trouvé ! " + e.getMessage());
            
            UpdateFich("Création du fichier de log !"); 
        } 
        catch (IOException e) 
        {
            e.getMessage();
        } 
        return logfich; 
    }
    
    public static void UpdateFich(String ligne)
    {
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = form.format(new Date());
        String nomfich = FichierConfig.getNomsFichs("logs");
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
