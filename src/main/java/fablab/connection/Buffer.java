/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.connection;

import fablab.Services.ServiceBuffer;
import fablab.Services.ServiceConnection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

/**
 * A buffer with no memory is a fake buffer.
 * 
 * @author cordieth
 */

@Component
@Provides
@Instantiate
public class Buffer implements ServiceBuffer{
    
    @Requires
    private ServiceConnection connection;
    private LinkedList<String> list;
    private ListIterator<String> it;

    public void sendData(String data) {
        if (connection.sendData(data)) {
            try {
                while (connection.sendData(list.remove())) //exception if empty, false if no more connection
                {}
            } catch (NoSuchElementException e) {
            }
        }
        else {
            list.add(data);
        }
    }
    
    
}
