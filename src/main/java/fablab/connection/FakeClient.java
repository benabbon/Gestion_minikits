/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.connection;
import fablab.Services.ServiceConnection;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Instantiate;

/**
 *
 * @author cordieth
 */

@Component
@Provides
@Instantiate
public class FakeClient implements ServiceConnection {

    public boolean sendData(String data) {
        System.out.println(data);
        return true;
    }
    
}
