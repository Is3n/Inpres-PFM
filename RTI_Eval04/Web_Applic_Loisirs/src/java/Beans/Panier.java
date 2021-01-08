/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;

/**
 *
 * @author isen0
 */
public class Panier {
    
    private ArrayList <Article> liste_articles = new ArrayList<Article>();

    public ArrayList<Article> getPanier() {
        return liste_articles;
    }

    public void sePanier(ArrayList<Article> liste_articles) {
        this.liste_articles = liste_articles;
    }
    
    public void addArticle(Article art) {
            liste_articles.add(art);
    }
    
    public void removeArticle(Article art) {
            liste_articles.remove(art);
    }
    
    public void findRemoveArticle (String idArt)
    {
        Article art;
        for (int i=0; i< liste_articles.size(); i++)
        {
            art = liste_articles.get(i);
            if (art.getIdArticle().equals(idArt))
            {
                liste_articles.remove(i);   
                i = liste_articles.size();
            }
        }
    }
}
