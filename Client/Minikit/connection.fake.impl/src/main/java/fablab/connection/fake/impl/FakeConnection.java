/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.connection.fake.impl;

/**
 *
 * @author cordieth
 */

import fablab.connection.service.Connection;
import fablab.config.service.Config;

public class FakeConnection implements Connection {

     Config conf;
    public boolean sendData(String data) {
       // System.out.println("config"+ conf.getIdMinikit());
        System.out.println(data);
        return true;
    }
    
}