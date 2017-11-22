package util;


import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fluffy
 */
public class HostDetails {

    public static final int PORT_CLOSED=0;
    public static final int PORT_OPEN=1;
    
    private  Map<Integer,Integer> ports=new HashMap<>();
    private  List<Route> traceroutes=new ArrayList<>();
    private Integer uptime;
    private String bootTime;
    private String OS;
    private String originaliyFactor;
    
    public synchronized void   portHandler(Integer port,Integer type){
        if(type==PORT_OPEN)
            System.out.println(port +" is open");
        
        if(ports.get(port)==null){
             ports.put(port, type);
            System.out.println("New Port Added "+ports.size());
             
        return;
        }
        
        if(ports.get(port)==type){
            System.out.println("New Already Exist");
            return;
        }
        else{
        //TO-DO calculate threshold
        ports.put(port, type);
        }
    }

    public Map<Integer, Integer> getPorts() {
        return ports;
    }

    public void setPorts(Map<Integer, Integer> ports) {
        this.ports = ports;
    }

    public List<Route> getTraceroutes() {
        return traceroutes;
    }

    public void setTraceroutes(List<Route> traceroutes) {
        this.traceroutes = traceroutes;
    }

    public Integer getUptime() {
        return uptime;
    }

    public void setUptime(Integer uptime) {
        this.uptime = uptime;
    }

    public String getBootTime() {
        return bootTime;
    }

    public void setBootTime(String bootTime) {
        this.bootTime = bootTime;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getOriginaliyFactor() {
        return originaliyFactor;
    }

    public void setOriginaliyFactor(String originaliyFactor) {
        this.originaliyFactor = originaliyFactor;
    }
    
    
    
}
