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
    private Client connexion;
	private int idMiniKit;
    public HeartBeat(Client c,int idMiniKit){
        this.connexion = c;
		this.idMiniKit=idMiniKit;
    }
 
    static private long period = 10000;  //heartbeat period in milliseconds
    
    public void run(){
        // setup the hb datagram packet then run forever
        String msg = "HB:"+idMiniKit;
     
                                  
                                  
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
