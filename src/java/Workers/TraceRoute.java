/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Workers;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fluffy
 */
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import util.HostDetails;
import util.Network;
import util.Route;

//update host details
public class TraceRoute extends Thread  implements Runnable{
    private String ip;

    public TraceRoute(String ip) {
    this.ip=ip;
    }
    
    private static synchronized void execute(String ip){
        try {
           
            
            
            NetworkInterface device=null;
            //initialize Jpcap
            int c=0;  
            for(NetworkInterface i:JpcapCaptor.getDeviceList()){
                System.out.println(i.description + i.name);
              device=i;
              if(c==2)
                  break;
              c++;
                          
              }
           
            JpcapCaptor captor=JpcapCaptor.openDevice(device,2000,false,5000);
            InetAddress thisIP=null;
            for(NetworkInterfaceAddress addr:device.addresses){
                System.out.println(addr.address.getHostAddress());
                if(addr.address instanceof Inet4Address){
                    thisIP=addr.address;
                    break;
                }
            }
            
            //create ICMP packet
            ICMPPacket icmp=new ICMPPacket();
            icmp.type=ICMPPacket.ICMP_ECHO;
            icmp.seq=100;
            icmp.id=0;
            icmp.setIPv4Parameter(0,false,false,false,0,false,false,false,0,0,0,IPPacket.IPPROTO_ICMP,
                    thisIP,InetAddress.getByName(ip));
            icmp.data="data".getBytes();
            
            EthernetPacket ether=new EthernetPacket();
            ether.frametype=EthernetPacket.ETHERTYPE_IP;
            ether.src_mac=device.mac_address;
            byte[] destmac=new byte[6];
            destmac[0]=12;
            destmac[1]=-46;
            destmac[2]=-75;
            destmac[3]=119;
            destmac[4]=31;
            destmac[5]=84;
            ether.dst_mac=destmac;
            icmp.datalink=ether;
            
            captor.setFilter("icmp and dst host "+thisIP.getHostAddress(),true);
           // captor.setFilter("icmp and dst host 192.168.137.1",true);
            JpcapSender sender=captor.getJpcapSenderInstance();
            //JpcapSender sender=JpcapSender.openDevice(device);
            sender.sendPacket(icmp);
            HostDetails hd=Network.getHost(ip);
            
            while(true){
                ICMPPacket p=(ICMPPacket) captor.getPacket();
                //System.out.println("received "+p);
                if(p==null){
                    System.out.println("Timeout");
                    break;
                }else if(p.type==ICMPPacket.ICMP_TIMXCEED){
                   // p.src_ip.getHostName();
                    System.out.println(icmp.hop_limit+": "+p.src_ip);
                    hd.handleRoute((int)icmp.hop_limit,p.src_ip.getHostAddress());
                    icmp.hop_limit++;
                }else if(p.type==ICMPPacket.ICMP_UNREACH){
                  //  p.src_ip.getHostName();
                    System.out.println(icmp.hop_limit+": "+p.src_ip);
                    hd.handleRoute((int)icmp.hop_limit,p.src_ip.getHostAddress());
                    break;
                }else if(p.type==ICMPPacket.ICMP_ECHOREPLY){
                   // p.src_ip.getHostName();
                    System.out.println(icmp.hop_limit+": "+p.src_ip);
                     hd.handleRoute((int)icmp.hop_limit,p.src_ip.getHostAddress());
                    break;
                }else continue;
                sender.sendPacket(icmp);
            }
        } catch (IOException ex) {
            Logger.getLogger(TraceRoute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public synchronized void run() {
      super.run(); //To change body of generated methods, choose Tools | Templates.
           execute(ip);
    }
    
    
    
}
