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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nabilbenabbou1
 */
public class Client_tcp {
    
    public static void main(String [] args) {
        try {
            String sentence;
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
            while (true) {
                System.out.println("Waiting to send ...");
                sentence = inFromUser.readLine();
                sendData(sentence);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client_tcp.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    public static synchronized String sendData(String data) {
        Socket clientSocket = null;
        String respond = null;
        try {
            clientSocket = new Socket("localhost", 1705);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            // If we need to wait for data from the server
            outToServer.writeBytes(data + '\n');
            if (data.startsWith("FIRST")) {
                BufferedReader inFromServer =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                respond = inFromServer.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(clientSocket!=null) try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client_tcp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respond;
    }
}
