/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.buffer.service;

/**
 *
 * @author cordieth
 */
public interface BufferService {
    
    /**
     * Send data to server or store it if there is connection problems.
     * 
     * @param data msg sent
     */
    
    public void sendData(String data);
    
    /**
     * Check is the buffer is Empty
     * 
     * @return emptiness of the buffer
     */
    public boolean isEmpty();
    
    /**
     * Try to send the data the buffer still have.
     */
    public void emptyList();
    
}
