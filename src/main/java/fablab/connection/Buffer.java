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
 * working buffer.
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
            emptyList();
        }
        else {
            list.add(data);
        }
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public void emptyList() {
        boolean workingConnection = !isEmpty();
        String data;
        while (workingConnection) {
            try {
                data=list.remove();
                if (!connection.sendData(data)) { // !!effet de bord!!
                    list.add(data);
                    workingConnection=false;
                }
            } catch (NoSuchElementException e) {
            }
        }
    }
}
