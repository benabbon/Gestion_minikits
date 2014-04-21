/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.MiniKit;
import fablab.HeartBeat.HeartBeat;
import fablab.connection.Client;
import java.util.HashMap;
/**
 *
 * @author toto
 */
public class MiniKit {
    
    private static MiniKit minikit = null;
    private int idMinikit;
    private Boolean firstConnexion;
    private String description;
    private Capteur[] capteurs;
    final private int nbCapteur = 1;
    private HeartBeat heartbeat;
    Client c;
    
    
   private MiniKit(){
       this.firstConnexion = true;
       this.capteurs = new Capteur[nbCapteur];
       c = new Client();
       
       String result = c.sendData("FIRST:"+"test:1");
	   heartbeat = new HeartBeat(c,Integer.parseInt(result));
       System.out.println("Result from server, first cnx : "+result);
	   for ( int i = 0; i < nbCapteur;i++){
           capteurs[i] = new Capteur(c,i+1,Integer.parseInt(result));
           capteurs[i].start();
       }
       
      heartbeat.start();   
   } 
   public Capteur getCapteur(int i){
       return capteurs[i];
   }
   public static MiniKit g(){
       if (minikit == null)
           minikit = new MiniKit();
       return minikit;
   }
}
