/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.buffer.impl;

/**
 *
 * @author cordieth
 */

import fablab.connection.service.Connection;
import fablab.buffer.service.BufferService;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Buffer implements BufferService {
    
    private Connection connection;
    private LinkedList<String> list;
    
    /**
     * Send data to server or store it if there is connection problems.
     * 
     * @param data msg sent
     */

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
