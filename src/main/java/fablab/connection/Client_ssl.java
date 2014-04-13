/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author nabilbenabbou1
 */
public class Client_ssl {
  
    public static void main(String [] args) {
        SSLSocket socket = null;
        PrintWriter out = null;
        try {
            //load client private key
            KeyStore clientKeys = KeyStore.getInstance("JKS");
            clientKeys.load(new FileInputStream("crt/client.jks"),"fablab".toCharArray());
            KeyManagerFactory clientKeyManager = KeyManagerFactory.getInstance("SunX509");
            clientKeyManager.init(clientKeys,"fablab".toCharArray());
            //load server public key
            KeyStore serverPub = KeyStore.getInstance("JKS");
            serverPub.load(new FileInputStream("crt/serverpub.jks"),"fablab".toCharArray());
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
            trustManager.init(serverPub);
            //use keys to create SSLSoket
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(clientKeyManager.getKeyManagers(), trustManager.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG"));
            socket = (SSLSocket)ssl.getSocketFactory().createSocket("195.221.228.9", 1705);
            socket.startHandshake();
            //send data
            InputStream inputstream = System.in;
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);           
            OutputStream outputstream = socket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                bufferedwriter.write(string + '\n');
                bufferedwriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out!=null) out.close();
            try {
                if(socket!=null) socket.close();
                if(socket!=null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
