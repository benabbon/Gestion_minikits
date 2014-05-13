/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.capteur.gestionnaire;

/**
 *
 * @author Thomas
 */

import fablab.capteur.service.CapteurService;
import fablab.config.service.Config;
import fablab.buffer.service.BufferService;

public class Gestionnaire implements CapteurService {
    
    BufferService buffer;
    Config config;
    
    public void sendData(String data, int numCapteur) {
        buffer.sendData(config.getIdMinikit()+" | "+numCapteur+" | "+data);
    }

    public void sendRemainData() {
        buffer.emptyList();
    }
    
    
}
