package fablab.HeartBeat;
import fablab.Services.ServiceConnection;
import fablab.connection.Client;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;

@Component
@Instantiate
public class HeartBeat extends Thread
{
    @Requires
    private ServiceConnection connexion;
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
