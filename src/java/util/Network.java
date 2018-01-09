package util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fluffy
 */
public class Network {
    
    public static final Integer INTERFACE=1;
    public static final ThresholdFactor GLOBAL_THRESHOLD=new ThresholdFactor();
    private static Map<String,String> arp=new HashMap<>();
    private static Map<String,HostDetails> host=new HashMap<>();
    
    static{
    
        GLOBAL_THRESHOLD.setPort( 5.5F );
        GLOBAL_THRESHOLD.setOS( 5.5F );
        GLOBAL_THRESHOLD.setRoute(5.5F );
        GLOBAL_THRESHOLD.setUptime(5.5F );
        updateGlobalThreshold();
    }
    public static HostDetails getHost(String ip){
    
    return host.get(ip);
    }
    
    public static void  addHost(String ip,HostDetails hd){
    host.put(ip, hd);
    
    }
    
    public static Map<String,String> getARPTable(){
    return arp;
    }
    
    public static Set<String> getAllIP(){
    return arp.keySet();
    }
    
    public static void addIP(String ip,String mac){
    if(arp.get(ip)==null)
        arp.put(ip, mac);
    }
    
    public static String getMAC(String ip){
    return arp.get(ip);
    }
    
    
    public static String hexToIp(String hex){
    
    return String.valueOf( Integer.parseInt(hex.substring(0, 2), 16 ) )+"."
            +String.valueOf( Integer.parseInt(hex.substring(2, 4), 16 ) )+"."
            +String.valueOf( Integer.parseInt(hex.substring(4, 6), 16 ) )+"."
            +String.valueOf( Integer.parseInt(hex.substring(6, 8), 16 ) );
    }
    //use host map inverse metrics
    public static void updateGlobalThreshold(){
     //update global details
        
     
     
     
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                }
                updateGlobalThreshold();
            }
        }).start();
    return ;
    }
    
}
