package fablab.client.heartbeat;
import fablab.connection.service.Connection;

public class HeartBeat implements Runnable
{
    private boolean m_end;
    private Connection connexion;
    /*public HeartBeat(Client c){
        this.connexion = c;
    }*/
 
    static private long period = 60000;  //heartbeat period in milliseconds
    
    public void run(){
        // setup the hb datagram packet then run forever
        String msg = "1";
     
                                  
                                  
        // continually loop and send this packet each period
        while (!m_end){
          try { 
               connexion.sendData(msg);
                Thread.sleep(period);  
            }
            catch (InterruptedException e){}
        }
    }
     /**
     * Starting.
     */
    public void starting() {
        Thread thread = new Thread(this);
        m_end = false;
        thread.start();
    }

    /**
     * Stopping.
     */
    public void stopping() {
        m_end = true;
    }
    
}
