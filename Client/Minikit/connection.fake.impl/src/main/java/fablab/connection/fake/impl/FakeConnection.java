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

public class FakeConnection implements Connection {

    public boolean sendData(String data) {
        System.out.println(data);
        return true;
    }
    
}