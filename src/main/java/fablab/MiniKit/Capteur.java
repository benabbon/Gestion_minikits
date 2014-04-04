/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.MiniKit;
import fablab.connection.Client;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author toto
 */
class Capteur extends Thread {
   // private HashMap<Integer,String> cache;
    private Client connexion;
    final private Signal signal = new Signal();
    private String message;
    
    public Signal getSignal(){
        return signal;
    }
    private void sendMessage(){
        System.out.println(message);
        //connexion.sendData(message);
    }
    public Capteur(Client c){
        connexion = c;
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
