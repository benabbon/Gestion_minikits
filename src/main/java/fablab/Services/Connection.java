/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.Services;

/**
 *
 * @author cordieth
 */
public interface Connection {
    
    /**
     * Send data to server using the connection.
     * If not succesfully sent, return false.
     * 
     * @param data msg sent
     * @return Indicate if the message was succesfully sent
     */
    
    public boolean sendData(String data);
}
