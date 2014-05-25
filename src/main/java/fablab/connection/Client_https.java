/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package fablab.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nabilbenabbou1
 */
public class Client_https {

    public static synchronized String sendData(String data) {
        String result = "";
        try {
            // Prepare the request
            System.setProperty("jsse.enableSNIExtension", "false");
            String httpsURL = "http://172.23.7.138:8080/fablab/Serveur_https";   
            // Encode data
            String query = "data="+URLEncoder.encode(data,"UTF-8");            
            URL myurl = new URL(httpsURL);
            HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-length", String.valueOf(query.length()));
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
            con.setDoOutput(true);
            con.setDoInput(true);
            DataOutputStream output = new DataOutputStream(con.getOutputStream());            
            output.writeBytes(query);            
            output.close();
            // Handle the response
            DataInputStream input = new DataInputStream( con.getInputStream() ); 
            String line = input.readLine();
            while (line != null) {
                result = result+line;
                line = input.readLine();
            }
            input.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Client_https.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client_https.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client_https.class.getName()).log(Level.SEVERE, null, ex);
        }
        // If the response is empty we send a null string
        if ("".equals(result))
            result = null;
        return result;
    }
    
    public static void main(String[] args) {
              sendData("FIRST:Nabil:3:LALa:LALA");
    }
    
}