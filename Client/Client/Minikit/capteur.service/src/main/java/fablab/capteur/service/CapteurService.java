/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.capteur.service;

/**
 *
 * @author Thomas
 */
public interface CapteurService {
    
    /**
     * Send the data passing by the buffer and the connection.
     * 
     * @param data Data the captor emits
     * @param numCapteur Id of the captor that send the data
     */
    public void sendData(String data, int numCapteur);
    
    /**
     * Try to send data remaining in the buffer.
     */
    public void sendRemainData();
}
