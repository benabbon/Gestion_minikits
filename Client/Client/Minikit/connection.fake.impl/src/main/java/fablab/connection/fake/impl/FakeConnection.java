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
import javax.swing.JFrame;

public class FakeConnection implements Connection {
    
    boolean activated;
    GUI gui;
    Config conf;
    public boolean sendData(String data) {
       // System.out.println("config"+ conf.getIdMinikit());
        if (activated) {
            System.out.println(data);
            return true;
        }
        else {
            return false;
        }
    }
    
    public void change() {
        activated=!activated;
    }
    
    public void starting() {
        gui = new GUI(this);
        activated=true;
        gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gui.setVisible(true);
        
    }

    /**
     * Stopping.
     */
    public void stopping() {
        gui.setVisible(false);
    }
}