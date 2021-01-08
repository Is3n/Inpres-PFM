/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_jchat_pfm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author isen0
 */
public class FichierConfig {
    
    public FichierConfig()
    {
    }
    
    public static String getNomsFichs(String str)
    {
        String nomF = new String();
        if(str.equals("err"))
            nomF = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "Err.jpg";
        
        if(str.equals("discuss"))
            nomF = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "Discuss.jpg";

        if(str.equals("config"))
            nomF = System.getProperty("user.dir") + System.getProperty("file.separator")+ "configJChat.properties";

        if(str.equals("logs"))
            nomF = System.getProperty("user.dir") + System.getProperty("file.separator") + getlogs();
        
        return nomF;
    }
    
    public static String getdateConfig()
    {
        Properties proplog = new Properties();
        String config = getNomsFichs("config");
        
        try 
        {
            proplog.load(new FileInputStream(config));
        } 
        catch(FileNotFoundException e) { System.out.println("Fichier properties non trouvé !"); }
        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); }
        
        String date = proplog.getProperty("FormatDate");
        String temps = proplog.getProperty("FormatTemps");
        
        String str = date + "-" + temps;
        return str;
    }
    
    public static String getlogs()
    {
        Properties proplog = new Properties();
        String config = getNomsFichs("config");
        
        try 
        {
            proplog.load(new FileInputStream(config));
        } 
        catch(FileNotFoundException e) { System.out.println("Fichier properties non trouvé !"); }
        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); }
        
        String logs =proplog.getProperty("Logs");
        
        return logs;
    }
    
    public static String get(String key)
    {
        Properties prop = new Properties();
        String config = getNomsFichs("config");
        
        try
        {
            prop.load(new FileInputStream(config));
            return prop.getProperty(key);
        }
        catch(FileNotFoundException e) { System.out.println("Fichier properties non trouvé !"); }
        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); }
        return null;
    }
    
}
