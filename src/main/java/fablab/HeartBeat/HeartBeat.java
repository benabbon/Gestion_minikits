import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

	
public class HeartBeat extends Thread
{

    public HeartBeat (InetAddress maddr,int port)
    { 
        this.maddr= maddr;
        this.port = port;
        try {
 			this.sock = new DatagramSocket();
 		} catch (SocketException e) {}
    }
    
    private DatagramSocket sock ;
    private InetAddress maddr;
    private int port;
    private DatagramPacket hbMsg ;
    static private long period = 60000;  //heartbeat period in milliseconds
    
    public void run(){
        // setup the hb datagram packet then run forever
        String msg = "1";
      
             this.sock.connect(maddr, port);
         
        hbMsg = new DatagramPacket(msg.getBytes(),
                                   msg.length(),
                                   maddr,
                                   port);
                                  
                                  
        // continually loop and send this packet each period
        while (true){
            try{
                sock.send(hbMsg);
                sleep(period);
              }
            catch (IOException e){System.err.println("Serveur disconnected");
                                  System.exit(-1);
            }
            catch (InterruptedException e){}
        }
    }// end run
    
}
