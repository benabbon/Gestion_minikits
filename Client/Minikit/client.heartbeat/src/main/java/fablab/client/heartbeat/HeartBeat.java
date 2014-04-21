package fablab.client.heartbeat;
import fablab.connection.service.Connection;

public class HeartBeat extends Thread
{
    private Connection connexion;
    /*public HeartBeat(Client c){
        this.connexion = c;
    }*/
 
    static private long period = 60000;  //heartbeat period in milliseconds
    
    public void run(){
        // setup the hb datagram packet then run forever
        String msg = "1";
     
                                  
                                  
        // continually loop and send this packet each period
        while (true){
          try { 
               connexion.sendData(msg);
                sleep(period);  
            }
            catch (InterruptedException e){}
        }
    }
    
}