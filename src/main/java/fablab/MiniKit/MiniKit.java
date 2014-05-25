/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.MiniKit;
import fablab.HeartBeat.HeartBeat;
import fablab.connection.Client;
import fablab.connection.Client_https;
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
    final private int nbCapteur = 2;
    private HeartBeat heartbeat;
    Client c;
    
    
   private MiniKit(){
       this.firstConnexion = true;
       this.capteurs = new Capteur[nbCapteur];
       c = new Client();
       
       String result = c.sendData("FIRST:"+"mardi:2");
	   idMinikit=Integer.parseInt(result);
	   heartbeat = new HeartBeat(c,idMinikit);
       System.out.println("Result from server, first cnx : "+result);
	   c.sendData("VALIDITE:"+idMinikit+":1:0:50");
	   c.sendData("VALIDITE:"+idMinikit+":2:0:50");
	   for ( int i = 0; i < nbCapteur;i++){
           capteurs[i] = new Capteur(c,i+1,Integer.parseInt(result));
           capteurs[i].start();
       }
       
      heartbeat.start();   
   } 
   public Capteur getCapteur(int i){
       return capteurs[i];
   }
   public Capteur[] getCapteurs(){
	   return capteurs;
   }
   public static MiniKit g(){
       if (minikit == null)
           minikit = new MiniKit();
       return minikit;
   }
}
