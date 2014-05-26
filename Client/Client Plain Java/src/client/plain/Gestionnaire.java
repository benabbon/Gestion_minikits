/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.plain;

/**
 *
 * @author Thomas
 */

import java.util.Date;

public class Gestionnaire {
    
    Buffer buffer;
    ConfigImpl config;
    
    public void sendData(String data, int numCapteur) {
        Date dateDonnee = new Date();
        buffer.sendData("DATA:"+config.getIdMinikit()+":"+numCapteur+":"+data+":"+dateDonnee.getTime());
    }

    public void sendValide(int numCapteur ,int min, int max) {
        buffer.sendData("VALIDITE:"+config.getIdMinikit()+":"+numCapteur+":"+min+":"+max);
        
    }
    
    public void sendRemainData() {
        buffer.emptyList();
    }

    public Gestionnaire(Buffer buffer, ConfigImpl conf) {
        this.buffer=buffer;
        this.config=conf;
    }
    
    
}
