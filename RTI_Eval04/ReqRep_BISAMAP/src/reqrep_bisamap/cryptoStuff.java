/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_bisamap;

import javax.crypto.SecretKey;

/**
 *
 * @author isen0
 */
public class cryptoStuff {
    
    private static SecretKey cleSecrete1=null;

    public static SecretKey getCleSecrete1() {
        return cleSecrete1;
    }

    public static void setCleSecrete1(SecretKey cleSecrete1) {
        cryptoStuff.cleSecrete1 = cleSecrete1;
    }
    
    private static SecretKey cleSecrete2=null;

    public static SecretKey getCleSecrete2() {
        return cleSecrete2;
    }

    public static void setCleSecrete2(SecretKey cleSecrete2) {
        cryptoStuff.cleSecrete2 = cleSecrete2;
    }
    
    
}
