/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.MiniKit;
import fablab.Services.ServiceBuffer;
import fablab.Services.ServiceConnection;
import fablab.connection.Client;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;

/**
 *
 * @author toto
 */

@Component
@Instantiate
class Capteur extends Thread {
   // private HashMap<Integer,String> cache;
    //@Requires
    //private ServiceBuffer connexion;
    
    private Client connexion;
    
    final private Signal signal = new Signal();
    private String message;
	private int idCapteur;
	private int idMiniKit;
    
    public Signal getSignal(){
        return signal;
    }
    private void sendMessage(){
        //System.out.println(message);
		Date dateDonnee = new Date();
        connexion.sendData("DATA:"+idMiniKit+":"+idCapteur+":"+message+":"+dateDonnee.getTime());
    }
    
    public Capteur(Client c, int id, int miniKit){
        connexion = c;
		this.idCapteur=id;
		this.idMiniKit=miniKit;
        //cache = new HashMap<Integer, String>();
        
    }
    
    @Override
    public void run(){
       while(true){
        synchronized(signal){
            try {
                signal.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Capteur.class.getName()).log(Level.SEVERE, null, ex);
            }
            message = signal.getMessage();
            sendMessage();
         }
        }
    }
      
}
