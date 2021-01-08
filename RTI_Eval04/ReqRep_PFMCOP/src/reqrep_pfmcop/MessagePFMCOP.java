/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_pfmcop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author isen0
 */
public class MessagePFMCOP implements Serializable {
    public static final int NO_TYPE         = 0;
    public static final int POST_QUESTION   = 1;
    public static final int ANSWER_QUESTION = 2;
    public static final int POST_EVENT      = 3;

    private int type;
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    private int tag;
    public void setTag(int tag) {
        this.tag = tag;
    }
    public int getTag() {
        return tag;
    }

    private String userName;
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    private String content;
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    private byte[] digest;
    public void setDigest(byte[] digest) {
        this.digest = digest;
    }
    public byte[] getDigest() {
        return digest;
    }

    private MessagePFMCOP() {

    }

    public MessagePFMCOP(int type, int tag, String userName, String content){
        this.setType(type);
        this.setTag(tag);
        this.setUserName(userName);
        this.setContent(content);
        this.digest=null;
    }

    public MessagePFMCOP(int type, String userName, String content){
        this(type,-1,userName,content);
    }

    public MessagePFMCOP(String userName, String content){
        this(NO_TYPE, -1, userName, content);
    }

    public MessagePFMCOP(int type, int tag, String userName, String content, byte[] digest){
        this.setType(type);
        this.setTag(tag);
        this.setUserName(userName);
        this.setContent(content);
        this.setDigest(digest);
        //System.out.println("TEST setDigest Question : "+digest);

    }

    @Override
    public String toString() {
        if(this.getType() == POST_QUESTION)
            return this.getUserName() + " >> [Question : " + this.getTag() + "] " + this.getContent();
        if(this.getType() == ANSWER_QUESTION)
            return this.getUserName() + " >> [Réponse : " + this.getTag() + "] " + this.getContent();
        if(this.getType() == POST_EVENT)
            return this.getUserName() + " >> [Event : " + this.getTag() + "] " + this.getContent();
        return this.getUserName() + " >> " + this.getContent();
    }

    public static int generateTag(){
        Random r = new Random();
        return (int) r.nextInt(10000) + 1;
    }

    public static MessagePFMCOP createMessage(String message){
        StringTokenizer tokenizer = new StringTokenizer(message,"#");
        System.out.println("createMessage : "+message);

        MessagePFMCOP m = new MessagePFMCOP();
        m.setType(Integer.parseInt(tokenizer.nextToken()));
        m.setUserName(tokenizer.nextToken());
        m.setContent(tokenizer.nextToken());
        if(tokenizer.hasMoreTokens()){
            m.setTag(Integer.parseInt(tokenizer.nextToken()));
        }
        if(tokenizer.hasMoreTokens()){
            //m.setDigest(tokenizer.nextToken().getBytes());
            
        // ICI, je reconstruit un digest car le digest construit lors de la creation de la question dans FenChat à été transformer en String
        // lors de l'appel à la méthode toStringTokMessage() (pour envoyer le message sur le réseau) et n'est donc plus égal à ce qu'il était avant d'être transformer.
        // Ce digest est exactement le même que celui construit dans FenChat et c'est celui ci qui sera utilisé pour vérifier
        // l'intégrité de la question lors de la création de la réponse.
            Properties prop = new Properties();
            String config = System.getProperty("user.dir") + System.getProperty("file.separator")+ "configJChat.properties";
            //System.out.println("Path fichier config MessagePFMCOP : "+config);
            try
            {
                prop.load(new FileInputStream(config));
            }
            catch(FileNotFoundException e) { System.out.println("Fichier properties (MessagePFMCOP) non trouvé ! message : " + e.getMessage()); }
            catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); }  
                            
            try
            {
                MessageDigest md = MessageDigest.getInstance(prop.getProperty("ALGODIGEST"), prop.getProperty("PROVIDERCODE"));
                md.update(m.getContent().getBytes());
                byte[] msgDigest = md.digest();
                m.setDigest(msgDigest);
            }
            catch(NoSuchAlgorithmException | NoSuchProviderException e)
            {
                System.err.println("Erreur digest creation MessagePFMCOP ! ? [" + e + "]");
            }
            
            //System.out.println("TEST Digest createMessage : "+m.getDigest());
        }
        return m;
    }

    public String toStringTokMessage(){
        if(this.getDigest()!=null)
        {
            //System.out.println("TEST Digest toStringTokMessage : "+this.getDigest());
            return this.getType() + "#" + this.getUserName() + "#" + this.getContent() + "#" + this.getTag() + "#" + this.getDigest() + "\r";
        }
        else
            return this.getType() + "#" + this.getUserName() + "#" + this.getContent() + "#" + this.getTag() + "\r";
    }
}
