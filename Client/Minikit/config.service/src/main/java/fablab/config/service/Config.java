/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package fablab.config.service;

/**
 *
 * @author cordieth
 */
public interface Config {
    
    
    /**
     * Get IP of the server.
     * This information is given by reading the config.txt.
     * 
     * @return IP of the server the minikit should connect to
     */
    public String getIPServer();
    
}
