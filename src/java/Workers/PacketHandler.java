/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Workers;

import util.HostDetails;
import util.Network;

/**
 *
 * @author Fluffy
 */

//Handle add ips start other treads based on requirement for each handle requested
public class PacketHandler extends Thread implements Runnable{

    static Integer Count=0;
    private PacketHandler me;
    private String ip;
    private String mac;
    
    public PacketHandler(String ip,String mac) {
    me=this;
    this.ip=ip;
    this.mac=mac;
    this.setName("Packet Reader "+Count);
    Count++;
    }
    
    @Override
    public void run() {
        super.run(); 
        if(!mac.equals("00 00 00 00 00 00 ") && ! ip.substring(ip.lastIndexOf(".")).equals(".1") ){
                            System.out.println(ip+" : "+mac);
                            
                            Network.addIP(ip, mac);
                            if(Network.getHost(ip)==null){
                            Network.addHost(ip, new HostDetails());
                                System.out.println("New Host Found");
                                // new PortScanner(ip).start();
                                // new OSGuesser(ip).start();
                                 new TraceRoute(ip).start();
                            }
                           
        }
        
        
        
    }
    
    
    
}

//update local threshold
