/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.plain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JFrame;

/**
 *
 * @author Thomas
 */
public class RealConnection {
    
    boolean activated;
    GUI gui;
    ConfigImpl conf;
    public boolean sendData(String data) {
        if (activated) {
            return(sendDataFirst(data)==null);
        }
        else {
            return false;
        }
    }
    
    public void change() {
        activated = !activated;
    }
    
    public void starting() {
        gui = new GUI(this);
        activated = true;
        gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gui.setVisible(true);
        initConnection();
        
    }

    /**
     * Stopping.
     */
    public void stopping() {
        gui.setVisible(false);
        gui.dispose();
    }
/**
 *  inittialize connexion by updating idMinikit value if it equals -1
 */
    private void initConnection() {
            String val = null;
            if (conf.getIdMinikit() == -1){
                boolean valide=false;
             //   while (valide==false)
                        try {
                        val = sendDataFirst("FIRST:"+ conf.getNomParticulier() +
                        conf.getPrenomParticulier() + ":"+ conf.getNbCapteurs());
                        conf.setIdMinikit(Integer.parseInt(val));
                        valide=true;}
                        catch (Exception e) {
                            System.out.println(e.toString());
                        }
               //conf.writeFile();
            }
        
    }

    
    public synchronized String sendDataFirst(String data) {
        String result = "";
        System.out.println(data);
        try {
            // Prepare the request
            System.setProperty("jsse.enableSNIExtension", "false");
            String httpsURL = "http://"+conf.getIPServer()+"/receive_data";  
            System.out.println(conf.getIPServer());
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
            System.out.println("Receive : "+result);
            input.close();
        } catch (UnsupportedEncodingException ex) {result="fail";
        } catch (MalformedURLException ex) {result="fail";
        } catch (IOException ex) {result="fail";
        }
        // If the response is empty we send a null string
        if ("".equals(result))
            result = null;
        return result;
    }

    public RealConnection(ConfigImpl conf) {
        this.conf=conf;
        starting();
    }
    
    
}
