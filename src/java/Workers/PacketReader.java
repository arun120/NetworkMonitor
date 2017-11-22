/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Workers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Arp;
import util.Network;

/**
 *
 * @author Fluffy
 */

// Read WinPac send to handler
public class PacketReader extends Thread implements Runnable{
  
    static Integer Count=0;
    private PacketReader me;
    public PacketReader() {
        me=this;
    this.setName("Packet Reader "+Count);
    Count++;
    }
    
    
    private Pcap pcap;
    
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
         List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
        StringBuilder errbuf = new StringBuilder(); // For any error msgs  
  
        /*************************************************************************** 
         * First get a list of devices on this system 
         **************************************************************************/  
        int r = Pcap.findAllDevs(alldevs, errbuf);  
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {  
            System.err.printf("Can't read list of devices, error is %s", errbuf  
                .toString());  
            return;  
        }  
  
        System.out.println("Network devices found:");  
  
        int i = 0;  
        for (PcapIf device : alldevs) {  
            String description =  
                (device.getDescription() != null) ? device.getDescription()  
                    : "No description available";  
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);  
        }  
  
        PcapIf device = alldevs.get(Network.INTERFACE); // We know we have atleast 1 device  
        System.out  
            .printf("\nChoosing '%s' on your behalf:\n",  
                (device.getDescription() != null) ? device.getDescription()  
                    : device.getName());  
  
        /*************************************************************************** 
         * Second we open up the selected device 
         **************************************************************************/  
        int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 10 * 1000;           // 10 seconds in millis  
         pcap =  
            Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);  
  
        if (pcap == null) {  
            System.err.printf("Error while opening device for capture: "  
                + errbuf.toString());  
            return;  
        }  
  
        /*************************************************************************** 
         * Third we create a packet handler which will receive packets from the 
         * libpcap loop. 
         **************************************************************************/  
        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {  
  
            public void nextPacket(PcapPacket packet, String user) {  
                
                Arp arp=new Arp();
                if(packet.hasHeader(arp)){
                System.out.println("ARP pack");
                packet.getHeader(arp);
                   
                   // System.out.println(arp.getPacket());
                    JPacket pac=arp.getPacket();
                    byte[] bytes=new byte[pac.size()];
                    pac.getByteArray(0, bytes);
                    int i=1;
                    String sourceIP="";
                    String targetIP="";
                    String sourceMAC="";
                    String targetMAC="";
                    for(byte b:bytes){
                        //System.out.printf("%02X ",b);
                        if(i<=42)
                        if(i>=39)
                            targetIP+=String.format("%02X",b);
                        else if(i>=33)
                            targetMAC+=String.format("%02X ",b);
                        else if(i>=29)
                            sourceIP+=String.format("%02X",b);
                        else if(i>=23)
                            sourceMAC+=String.format("%02X ",b);
                        i++;
                        
                    }
                            sourceIP=Network.hexToIp(sourceIP);
                            targetIP=Network.hexToIp(targetIP);
                            new PacketHandler(sourceIP,sourceMAC).start();
                            new PacketHandler(targetIP, targetMAC).start();
                
                }else{
                System.out.println("Other pack ");
                }
                
                if(me.isInterrupted())
                    stopReader();
            }  
        };  
  
     pcap.loop(Pcap.LOOP_INFINITE , jpacketHandler, "");  
       
    
     System.out.print("stopped ");
       
        pcap.close();  
        
    }
    
    
    public void stopReader(){
    System.out.print("stop called ");
    pcap.breakloop();
    }
    
    
}
