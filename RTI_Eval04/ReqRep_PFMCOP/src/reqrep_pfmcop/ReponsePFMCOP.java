/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_pfmcop;

import java.io.Serializable;
import reqrep_poolthreads.Reponse;

/**
 *
 * @author isen0
 */
public class ReponsePFMCOP implements Reponse, Serializable
{
    public static int OK = 201;
    public static int FAIL = 501;
    public static int WRONG_PASSWORD = 401;
    private int codeRetour;
    private String chargeUtile;
    
    public ReponsePFMCOP(int c, String chu)
    {
        codeRetour = c; setChargeUtile(chu);
    }
    
    public int getCode() { return codeRetour; }
    
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile) { this.chargeUtile = chargeUtile; }
    
}
