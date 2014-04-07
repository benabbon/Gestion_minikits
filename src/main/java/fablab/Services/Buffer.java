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
public interface Buffer {
    
    /**
     * Send the data to the server using the connection.
     * If not succesfully sent keep the data in memory (in file in version 2? )
     * until the connection is back.
     * 
     * @param data 
     */
    public void sendData(String data);
}
