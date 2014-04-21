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
            welcomeSocket = new ServerSocket(1705);
            while(true)
            {
                connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                String respond = handle(clientSentence);
                if (respond != null) {
                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    outToClient.writeBytes(respond + '\n');
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(connectionSocket!=null) connectionSocket.close();
                // We recall the main in order not to lose the connection
                //main(args);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String handle(String data) {
        String respond = null;
        if (data != null) {
		if (data.startsWith	("HB")) {
            handleHeartBeat(data.substring(3));
        }
        if (data.startsWith("DATA")) {
            handleData(data.substring(5));
        }
        if (data.startsWith("FIRST")) {
            respond = handleFirstConnection(data.substring(6));
        }
		}
        return respond;
    }
    
    public static void handleHeartBeat(String data) {
		System.out.println("hb received");
		int id = Integer.parseInt(data);
		ServeurDB.HB(id);
    }
    
    public static void handleData(String data) {
        System.out.println("data received");
		String[] param = data.split(":");
		if(param.length >= 4){
			System.out.println("ana f la boucle");
			int idMiniKit = Integer.parseInt(param[0]);
			int idCapteur = Integer.parseInt(param[1]);
			int donnee  = Integer.parseInt(param[2]);
			long dateDonnee = Long.parseLong(param[3]);
			ServeurDB.donnee(idMiniKit,idCapteur,donnee, dateDonnee);
			
		}
    }

    private static String handleFirstConnection(String data) {
        String[] param = data.split(":");
		if(param.length >= 2){
			String client = param[0];
			int nbCapteur = new Integer(param[1]);
			int id = ServeurDB.premiereCnx(client,nbCapteur);
			return id+"";
		}
		return null;
    }
    

}