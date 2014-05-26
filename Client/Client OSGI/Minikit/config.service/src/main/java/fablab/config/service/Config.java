/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package fablab.config.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author cordieth
 */
public interface Config {
    
    
    /**
     * Get IP of the server.
     * This information is given by reading the config.txt.
     *
     * @return IP of the server the minikit should connect to
     */
    public String getIPServer();
    
   /**
    *  set server's IP
    * @param ipServer 
    */
    
    public void setIPServer(String ipServer);
    
    /**
     *  Get the lastname of the Minikit user
     * @return Lastname
     */
    
    public String getNomParticulier();
    
    public void setNomParticulier(String nom);
    
    /**
     *  Get the first name of the minikit user
     * @return firstname
     */
    public String getPrenomParticulier();
    
    public void setPrenomParticulier(String prenom);
    /**
     * Get the id of the Minikit if already defined -1 else.
     * @return idMinikit if defined
     */
    public int getIdMinikit();
    
    public void setIdMinikit(int id);
    
    /**
     *  Get the number of Captors
     * @return number of Captors
     */
    public int getNbCapteurs();
    
    public void setNbCapteurs(int nbcapteurs);
    /**
     *  get functionality of a capto ( one word of what the captors does)
     * @param numeroCapteur captor number 
     * @return Captor functionality
     */
    public String getFoncCapteur(int numeroCapteur);
    
    public void setFoncCapteur(int numeroCapteur,String func);

    public void writeFile() throws FileNotFoundException, IOException;
}
