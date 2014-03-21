/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package fablab.connection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;


/**
 *
 * @author nabilbenabbou1
 */
public class Server {
    public static void main(String [] args) {
        SSLServerSocket serverSock = null;
        SSLSocket socket = null;
        BufferedReader in = null;
        try {
            //load server private key
            KeyStore serverKeys = KeyStore.getInstance("JKS");
            serverKeys.load(new FileInputStream("crt/server.jks"),"fablab".toCharArray());
            KeyManagerFactory serverKeyManager = KeyManagerFactory.getInstance("SunX509");
            //System.out.println(KeyManagerFactory.getDefaultAlgorithm());
            //System.out.println(serverKeyManager.getProvider());
            serverKeyManager.init(serverKeys,"fablab".toCharArray());
            //load client public key
            KeyStore clientPub = KeyStore.getInstance("JKS");
            clientPub.load(new FileInputStream("crt/clientpub.jks"),"fablab".toCharArray());
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
            trustManager.init(clientPub);
            //use keys to create SSLSoket
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(serverKeyManager.getKeyManagers(), trustManager.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG"));
            serverSock = (SSLServerSocket)ssl.getServerSocketFactory().createServerSocket(8889);
            serverSock.setNeedClientAuth(true);
            socket = (SSLSocket)serverSock.accept();           
            //receive data
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data;
            while((data = in.readLine())!=null) {
                System.out.println(data);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(in!=null) in.close();
                if(serverSock!=null) serverSock.close();
                if(socket!=null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}