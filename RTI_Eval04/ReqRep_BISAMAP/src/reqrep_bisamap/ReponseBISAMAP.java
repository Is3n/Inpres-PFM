/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_bisamap;

import java.io.Serializable;
import javax.crypto.SecretKey;
import javax.swing.table.DefaultTableModel;
import reqrep_poolthreads.Reponse;

/**
 *
 * @author isen0
 */
public class ReponseBISAMAP implements Reponse, Serializable
{
    public static int OK = 201;
    public static int FAIL = 501;
    public static int FAIL_VERIF = 510;
    public static int WRONG_PASSWORD = 401;
    public static int WRONG_DATA = 402;
    private int codeRetour;
    private String chargeUtile;
    private DefaultTableModel model;
    private byte[] cryptoObj;
    private byte[] cryptoObj2;
    
    public ReponseBISAMAP(int c, String chu)
    {
        codeRetour = c; setChargeUtile(chu);
    }
    
    public ReponseBISAMAP(int c, DefaultTableModel model)
    {
        codeRetour = c; setModel(model);
    }
    
    public ReponseBISAMAP(int c, byte[] cryptoO)
    {
        codeRetour = c; setCryptoObj(cryptoO);
    }
    
    public ReponseBISAMAP(int c, byte[] cryptoO, byte[] cryptoO2)
    {
        codeRetour = c; setCryptoObj(cryptoO); setCryptoObj2(cryptoO2);
    }
    
    public int getCode() { return codeRetour; }
    
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile) { this.chargeUtile = chargeUtile; }
    
    public DefaultTableModel getModel() { return model; }
    public void setModel(DefaultTableModel mod) { this.model = mod; }

    public byte[] getCryptoObj() {
        return cryptoObj;
    }
    public void setCryptoObj(byte[] cryptoO) {
        this.cryptoObj = cryptoO;
    }

    public byte[] getCryptoObj2() {
        return cryptoObj2;
    }
    public void setCryptoObj2(byte[] cryptoObj2) {
        this.cryptoObj2 = cryptoObj2;
    }
    
    
}