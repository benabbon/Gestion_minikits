/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package fablab.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author nabilbenabbou1
 */
public class Server {
    public static void main(String [] args) {
        ServerSocket welcomeSocket = null;
        Socket connectionSocket = null;
        try {                         
            String clientSentence;
            welcomeSocket = new ServerSocket(6789);
            while(true)
            {
                connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                handle(clientSentence);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(connectionSocket!=null) connectionSocket.close();
                // We recall the main in order not to lose the connection
                main(args);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void handle(String data) {
        if (data.startsWith("HB")) {
            handleHeartBeat(data);
            return ;
        }
        if (data.startsWith("DATA")) {
            handleData(data);
            return;
        }
        
    }
    
    public static void handleHeartBeat(String data) {
        System.out.println("Received HeartBeat: " + data);
    }
    
    public static void handleData(String data) {
        System.out.println("Received Data: " + data);
    }
    

}