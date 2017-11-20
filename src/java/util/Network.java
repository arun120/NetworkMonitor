package util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private static Map<String,String> arp=new HashMap<>();
    private static Map<String,HostDetails> host=new HashMap<>();
    
    
    static HostDetails getHost(String ip){
    
    return host.get(ip);
    }
    
    static void  addHost(String ip,HostDetails hd){
    host.put(ip, hd);
    
    }
    
    static Map<String,String> getARPTable(){
    return arp;
    }
    
    static Set<String> getAllIP(){
    return arp.keySet();
    }
    
    static void addIP(String ip,String mac){
    
    arp.put(ip, mac);
    }
    
    static String getMAC(String ip){
    return arp.get(ip);
    }
}
